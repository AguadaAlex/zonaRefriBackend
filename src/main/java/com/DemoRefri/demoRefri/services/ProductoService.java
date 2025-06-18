package com.DemoRefri.demoRefri.services;

import com.DemoRefri.demoRefri.domain.ProductoDomain;
import com.DemoRefri.demoRefri.persistance.entities.Producto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    ProductoDomain crearProducto(ProductoDomain producto, MultipartFile file);
    ProductoDomain actualizarProducto(Integer id, ProductoDomain producto, MultipartFile file);
    void eliminarProducto(Integer id);
    List<ProductoDomain> obtenerProductosPorCategoria(String categoria);
    Optional<ProductoDomain> obtenerProductoPorId(Integer id);

    List<ProductoDomain> obtenerTodosLosProductos();
}
