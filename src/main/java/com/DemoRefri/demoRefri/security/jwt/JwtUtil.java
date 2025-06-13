package com.DemoRefri.demoRefri.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private static final String SECRET_STRING = "JfLSe14bYHB8UYqyIQUKYfGfQPIAyV2gZ/Vsc+kTj3s=";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));

    // 🛠 Generar JWT con roles
    public String generateToken(UserDetails userDetails) {
        List<String> roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority()) // ✅ Asegurar que tenga "ROLE_"
                .collect(Collectors.toList());

        System.out.println("🔐 Generando JWT para usuario: " + userDetails.getUsername());
        System.out.println("🛠 Roles en el token: " + roles);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", roles) // ✅ Agregar roles al token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expira en 1 hora
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // 🛠 Validar JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(SECRET_KEY) // ✅ Usa `verifyWith()` en lugar de `setSigningKey()`
                    .build()
                    .parseSignedClaims(token)
                    .getPayload(); // ✅ Usa `parseSignedClaims()` en lugar de `parseClaimsJws()`
            return true;
        } catch (Exception e) {
            System.err.println("Error validando token: " + e.getMessage());
            return false;
        }
    }

    // 🛠 Extraer email del JWT
    public String extractEmail(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY) // ✅ Usa `verifyWith()` en lugar de `setSigningKey()`
                .build()
                .parseSignedClaims(token)
                .getPayload(); // ✅ Usa `parseSignedClaims()` en lugar de `parseClaimsJws()`
        return claims.getSubject();
    }

    // 🛠 Extraer roles del JWT
    public List<String> extractRoles(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY) // ✅ Usa `verifyWith()` en lugar de `setSigningKey()`
                .build()
                .parseSignedClaims(token)
                .getPayload(); // ✅ Usa `parseSignedClaims()` en lugar de `parseClaimsJws()`
        List<String> roles = claims.get("roles", List.class);
        System.out.println("🔐 Roles extraídos del token: " + roles);
        return roles;
    }
}