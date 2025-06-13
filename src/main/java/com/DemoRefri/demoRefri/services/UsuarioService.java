package com.DemoRefri.demoRefri.services;

import com.DemoRefri.demoRefri.domain.UsuarioDomain;
import com.DemoRefri.demoRefri.persistance.entities.Usuario;

public interface UsuarioService {
    UsuarioDomain registrarUsuario(UsuarioDomain usuario);
    UsuarioDomain obtenerUsuarioPorEmail(String email);
}