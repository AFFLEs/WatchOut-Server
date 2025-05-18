package com.affles.watchout.server.global.jwt;

import com.affles.watchout.server.global.common.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.affles.watchout.server.global.status.ErrorStatus.LOGIN_REQUIRED;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        ApiResponse<?> errorResponse = ApiResponse.onFailure(
                LOGIN_REQUIRED.getMessage(),
                LOGIN_REQUIRED.getHttpStatus().value(),
                null
        );

        response.setStatus(LOGIN_REQUIRED.getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        new ObjectMapper().writeValue(response.getWriter(), errorResponse);
    }
}