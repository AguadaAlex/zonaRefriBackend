package com.DemoRefri.demoRefri.security;

import com.DemoRefri.demoRefri.persistance.entities.Usuario;
import com.DemoRefri.demoRefri.persistance.entities.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        System.out.println("🔍 Usuario autenticado: " + usuario.getEmail());
        System.out.println("🔐 Rol asignado desde DB: " + usuario.getRol());

        return new CustomUserDetails(usuario); // ✅ Envolver el usuario con roles compatibles
    }
}