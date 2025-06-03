package com.affles.watchout.server.global.jwt;

import com.affles.watchout.server.global.common.ApiResponse;
import com.affles.watchout.server.global.status.ErrorStatus;
import com.affles.watchout.server.global.redis.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        log.info("🛡️ [JWT 필터] 요청 URI: {}", uri);

        // 인증 제외 URI
        if (uri.startsWith("/api/users/login") ||
                uri.startsWith("/api/users/signup") ||
                uri.startsWith("/api/users/refresh")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");
        log.info("🔍 [Authorization 헤더] {}", authorizationHeader);

        String token = jwtUtil.resolveAccessToken(request);
        log.info("🔍 [AccessToken 파싱] {}", token);

        if (StringUtils.hasText(token)) {
            try {
                if (redisUtil.isTokenBlacklisted(token)) {
                    log.warn("⛔ 블랙리스트 토큰으로 요청됨");
                    setErrorResponse(response, ErrorStatus.TOKEN_NOT_FOUND);
                    return;
                }

                Claims claims = jwtUtil.getClaims(token);
                Long userId = Long.parseLong(claims.getSubject());

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.info("✅ [JWT 인증 성공] userId: {}", userId);

            } catch (ExpiredJwtException e) {
                log.warn("⚠️ [JWT 만료] {}", e.getMessage());
                setErrorResponse(response, ErrorStatus.TOKEN_NOT_FOUND);
            } catch (JwtException | IllegalArgumentException e) {
                log.warn("⚠️ [JWT 파싱 실패] {}", e.getMessage());
                setErrorResponse(response, ErrorStatus.TOKEN_NOT_FOUND);
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