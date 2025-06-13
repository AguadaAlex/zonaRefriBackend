package com.DemoRefri.demoRefri.controllers;

import com.DemoRefri.demoRefri.dto.LoginRequest;
import com.DemoRefri.demoRefri.security.jwt.JwtUtil;
import com.DemoRefri.demoRefri.persistance.entities.Usuario;
import com.DemoRefri.demoRefri.persistance.entities.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UserDetailsService userDetailsService; // ✅ Inyección para cargar usuario desde base de datos

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // ✅ Autenticar usuario
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            // ✅ Cargar `UserDetails` para obtener roles
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

            // ✅ Generar token con roles
            String token = jwtUtil.generateToken(userDetails);

            // ✅ Respuesta con token
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login exitoso para: " + loginRequest.getEmail());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales incorrectas"));
        }
    }
}