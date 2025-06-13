package com.DemoRefri.demoRefri.controllers;

import com.DemoRefri.demoRefri.domain.UsuarioDomain;
import com.DemoRefri.demoRefri.dto.UsuarioRequest;
import com.DemoRefri.demoRefri.services.UsuarioServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    @Autowired
    private UsuarioServiceImpl usuarioService;

    @PostMapping("/registrar")
    public UsuarioDomain registrarUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        System.out.println("Contraseña en request: " + usuarioRequest.getPassword()); // 📌 Depuración
        return usuarioService.registrarUsuario(
                new UsuarioDomain(
                        null, // ID generado automáticamente
                        usuarioRequest.getNombre(),
                        usuarioRequest.getEmail(),
                        usuarioRequest.getPassword()
                )
        );
    }
    @GetMapping("/{email}")
    public UsuarioDomain obtenerUsuarioPorEmail(@PathVariable String email) {
        return usuarioService.obtenerUsuarioPorEmail(email);
    }
}