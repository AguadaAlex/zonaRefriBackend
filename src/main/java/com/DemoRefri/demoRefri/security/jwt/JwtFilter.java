package com.DemoRefri.demoRefri.security.jwt;

import com.DemoRefri.demoRefri.security.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remover "Bearer " del token

            try {
                String email = jwtUtil.extractEmail(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                if (jwtUtil.validateToken(token)) {
                    // ✅ Asegurar que los roles tienen formato correcto ("ROLE_...")
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    // ✅ Imprimir roles en el log con formato claro
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (authentication != null) {
                        String roles = authentication.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.joining(", "));
                        System.out.println("🔍 Usuario autenticado: " + email);
                        System.out.println("🔐 Roles asignados: " + roles);
                    }

                    // ✅ Validar que el usuario tiene al menos un rol antes de continuar
                    if (authentication.getAuthorities().isEmpty()) {
                        System.err.println("⚠️ Usuario sin roles asignados. Bloqueando acceso.");
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado: el usuario no tiene roles.");
                        return;
                    }
                } else {
                    System.err.println("⚠️ Token inválido.");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o expirado");
                    return;
                }
            } catch (ExpiredJwtException e) {
                System.err.println("🚨 Token expirado.");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expirado");
                return;
            } catch (SignatureException e) {
                System.err.println("🚨 Token con firma inválida.");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Firma de token inválida");
                return;
            } catch (Exception e) {
                System.err.println("🚨 Error en la autenticación con JWT: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error al validar token");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}