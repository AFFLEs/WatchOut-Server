package com.affles.watchout.server.global.jwt;

import com.affles.watchout.server.global.common.ApiResponse;
import com.affles.watchout.server.global.status.ErrorStatus;
import com.affles.watchout.server.global.util.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtUtil.resolveToken(request);

        if (StringUtils.hasText(token)) {
            try {
                // 블랙리스트 확인
                if (redisUtil.isTokenBlacklisted(token)) {
                    setErrorResponse(response, ErrorStatus.TOKEN_NOT_FOUND); // or TOKEN_BLACKLISTED
                    return;
                }

                // Claims 파싱 → 예외 발생 가능
                Claims claims = jwtUtil.getClaims(token);

                // 유저 정보 꺼내기
                Long userId = Long.parseLong(claims.getSubject());

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (ExpiredJwtException e) {
                setErrorResponse(response, ErrorStatus.TOKEN_NOT_FOUND); // or TOKEN_EXPIRED
            } catch (JwtException | IllegalArgumentException e) {
                setErrorResponse(response, ErrorStatus.TOKEN_NOT_FOUND); // or TOKEN_INVALID
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setErrorResponse(HttpServletResponse response, ErrorStatus errorStatus) throws IOException {
        ApiResponse<?> apiResponse = ApiResponse.onFailure(
                errorStatus.getMessage(),
                errorStatus.getHttpStatus().value(),
                null
        );

        response.setStatus(errorStatus.getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        new ObjectMapper().writeValue(response.getWriter(), apiResponse);
    }
}
