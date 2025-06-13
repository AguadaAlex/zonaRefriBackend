package com.DemoRefri.demoRefri.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        String message = authException.getMessage().contains("expired")
                ? "El token ha expirado. Por favor, vuelve a iniciar sesión."
                : "No tienes permisos para realizar esta acción.";

        PrintWriter out = response.getWriter();
        out.print("{\"status\": 403, \"error\": \"Forbidden\", \"message\": \"" + message + "\", \"timestamp\": \"" + Instant.now() + "\"}");
        out.flush();
    }
}