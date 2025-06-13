package com.DemoRefri.demoRefri.domain;

import com.DemoRefri.demoRefri.enums.Rol;
import com.DemoRefri.demoRefri.persistance.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class UsuarioDomain {
    private Integer id;
    private String nombre;
    private String email;
    private String password;
    private Rol rol;
    private Date fechaRegistro;
        public  UsuarioDomain(Integer id){
            this.id=id;
        }

        public UsuarioDomain(Integer id, String nombre, String email, String password, Rol rol, Date fechaRegistro) {
            this.id = id;
            this.nombre = nombre;
            this.email = email;
            this.password = password;
            this.rol = rol;
            this.fechaRegistro = fechaRegistro;
        }

        public UsuarioDomain(Integer id, String nombre, String email, String password) {
            this.id = (id instanceof Integer) ? (Integer) id : null; // Manejo seguro de ID
            this.nombre = nombre;
            this.email = email;
            this.password = password;
            this.rol = Rol.CLIENTE; // Asignar un rol por defecto si no se especifica
            this.fechaRegistro = new Date(); // Definir fecha actual
        }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    // Método estático para convertir de Entity a Domain
    public static UsuarioDomain fromEntity(Usuario usuario) {
        if (usuario == null) {
            return null; // Evita errores con valores nulos
        }
        return new UsuarioDomain(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getRol(),
                usuario.getFechaRegistro()
        );
    }

    // Método para convertir de Domain a Entity
    public Usuario toEntity() {
        Usuario usuario = new Usuario();
        usuario.setId(this.id);
        usuario.setNombre(this.nombre);
        usuario.setEmail(this.email);
        usuario.setPassword(this.password);
        usuario.setRol(this.rol);
        usuario.setFechaRegistro(this.fechaRegistro);
        return usuario;
    }

    // Método de validación para asegurar datos válidos antes de la conversión
    public void validarUsuario() {
        if (this.nombre == null || this.nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (this.email == null || this.email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío.");
        }
        if (this.password == null || this.password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }
        if (this.rol == null) {
            throw new IllegalArgumentException("El rol no puede ser nulo.");
        }
    }

    // Método auxiliar para mostrar información del usuario
    @Override
    public String toString() {
        return "UsuarioDomain{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", rol=" + rol +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}