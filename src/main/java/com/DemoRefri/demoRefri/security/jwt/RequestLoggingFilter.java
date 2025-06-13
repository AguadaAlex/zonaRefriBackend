package com.DemoRefri.demoRefri.security.jwt;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter("/*")
public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        System.out.println("🔎 Solicitud recibida: " + req.getMethod() + " " + req.getRequestURI());

        chain.doFilter(request, response);
    }
}