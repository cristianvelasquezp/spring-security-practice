package org.cristianvelasquezp.springsecuritypractice.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        LocalDateTime currentTimeStamp = LocalDateTime.now();
        String message = ( authException != null && authException.getMessage() != null) ? authException.getMessage() : "Unauthorized";
        String path = request.getRequestURI();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        String messageJson = "{\"status\": \"" + HttpStatus.UNAUTHORIZED.value() + "\", \"message\": \"" + message + "\", \"path\": \"" + path + "\", \"timestamp\": \""
                + currentTimeStamp + "\"}";
        response.getWriter().write(messageJson);
    }
}
