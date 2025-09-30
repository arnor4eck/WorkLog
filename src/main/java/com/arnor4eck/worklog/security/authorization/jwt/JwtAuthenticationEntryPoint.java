package com.arnor4eck.worklog.security.authorization.jwt;

import com.arnor4eck.worklog.utils.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

// 401 Unauthorized
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        // FOR SERVER CHECK
        log.info("ip: %s; host name: %s; port: %d".formatted(request.getRemoteAddr(),
                request.getRemoteHost(),
                request.getRemotePort()));

        response.getWriter().write(
                new ObjectMapper().writeValueAsString(
                        new ExceptionResponse("Требуется авторизация: %s".formatted(authException.getMessage()))));
    }
}
