package com.DemoRefri.demoRefri.services;

import com.DemoRefri.demoRefri.domain.ProductoDomain;
import com.DemoRefri.demoRefri.persistance.entities.Producto;
import com.DemoRefri.demoRefri.persistance.entities.repositories.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public ProductoServiceImpl() {
        this.productoRepository = null; // Evita errores de inicialización
    }

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional
    public ProductoDomain crearProducto(ProductoDomain productoDomain) {
        Producto productoGuardado = productoRepository.save(productoDomain.toEntity());
        return ProductoDomain.fromEntity(productoGuardado);
    }

    @Override
    public ProductoDomain actualizarProducto(Integer id, ProductoDomain producto) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        productoExistente.setNombre(producto.getNombre());
        productoExistente.setDescripcion(producto.getDescripcion());
        productoExistente.setPrecio(producto.getPrecio());
        productoExistente.setStock(producto.getStock());
        productoExistente.setImagenUrl(producto.getImagenUrl());
        productoExistente.setCategoria(producto.getCategoria());

        Producto productoGuardado = productoRepository.save(productoExistente);

        System.out.println("Producto guardado en BD: " + productoGuardado); // 📌 Depuración

        return ProductoDomain.fromEntity(productoGuardado);
    }

    @Override
    public void eliminarProducto(Integer id) {
        productoRepository.deleteById(id);
    }

    @Override
    public List<ProductoDomain> obtenerProductosPorCategoria(String categoria) {
        List<Producto> productos = productoRepository.findByCategoria(categoria);
        return productos.stream()
                .map(ProductoDomain::fromEntity)
                .toList();
    }

    @Override
    public Optional<ProductoDomain> obtenerProductoPorId(Integer id) {
        return productoRepository.findById(id).map(ProductoDomain::fromEntity);
    }

    @Override
    public List<ProductoDomain> obtenerTodosLosProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(ProductoDomain::fromEntity)
                .toList();
    }
}