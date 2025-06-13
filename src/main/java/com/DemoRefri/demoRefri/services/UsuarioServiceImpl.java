package com.DemoRefri.demoRefri.services;

import com.DemoRefri.demoRefri.domain.UsuarioDomain;
import com.DemoRefri.demoRefri.persistance.entities.Usuario;
import com.DemoRefri.demoRefri.persistance.entities.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private  UsuarioRepository usuarioRepository; // ✅ Inyección segura con Lombok
    @Autowired
    private  BCryptPasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioDomain registrarUsuario(UsuarioDomain usuario) {
        System.out.println("Contraseña antes de encriptar: " + usuario.getPassword()); // 📌 Depuración
        usuario.setPassword(encriptarPassword(usuario.getPassword()));

        return UsuarioDomain.fromEntity(usuarioRepository.save(usuario.toEntity()));
    }
    @Override
    public UsuarioDomain obtenerUsuarioPorEmail(String email) {
        // Buscar usuario por email en la base de datos
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Convertir `Usuario` a `UsuarioDomain` antes de retornarlo
        return UsuarioDomain.fromEntity(usuario);
    }

    private String encriptarPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía");
        }
        return passwordEncoder.encode(password);
    }

}