package com.DemoRefri.demoRefri.domain;

import com.DemoRefri.demoRefri.persistance.entities.Producto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ProductoDomain {
    private Integer id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private String imagenUrl; // URL en AWS S3
    private String categoria;
    private Date fechaCreacion;

    public ProductoDomain(Integer id){
        this.id=id;
    }

    public ProductoDomain(Integer id, String nombre, String descripcion, BigDecimal precio, Integer stock, String imagenUrl, String categoria, Date fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagenUrl = imagenUrl;
        this.categoria = categoria;
        this.fechaCreacion = fechaCreacion;
    }

    // Método estático para convertir de Entity a Domain
    public static ProductoDomain fromEntity(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }

        return new ProductoDomain(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getImagenUrl() != null ? producto.getImagenUrl() : "", // ✅ Evitar null
                producto.getCategoria() != null ? producto.getCategoria() : "Sin categoría", // ✅ Evitar null
                producto.getFechaCreacion() != null ? producto.getFechaCreacion() : new Date() // ✅ Evitar null
        );

    }

    // Método para convertir de Domain a Entity
    public Producto toEntity() {
        Producto producto = new Producto();
        producto.setId(this.id);
        producto.setNombre(this.nombre);
        producto.setDescripcion(this.descripcion);
        producto.setPrecio(this.precio);
        producto.setStock(this.stock);
        producto.setImagenUrl(this.imagenUrl);
        producto.setCategoria(this.categoria);
        producto.setFechaCreacion(this.fechaCreacion);
        return producto;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}