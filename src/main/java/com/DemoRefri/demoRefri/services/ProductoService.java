package com.DemoRefri.demoRefri.services;

import com.DemoRefri.demoRefri.domain.ProductoDomain;
import com.DemoRefri.demoRefri.persistance.entities.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    ProductoDomain crearProducto(ProductoDomain producto);
    ProductoDomain actualizarProducto(Integer id, ProductoDomain producto);
    void eliminarProducto(Integer id);
    List<ProductoDomain> obtenerProductosPorCategoria(String categoria);
    Optional<ProductoDomain> obtenerProductoPorId(Integer id);

    List<ProductoDomain> obtenerTodosLosProductos();
}
