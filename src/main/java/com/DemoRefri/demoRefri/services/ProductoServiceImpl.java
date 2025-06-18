package com.DemoRefri.demoRefri.services;

import com.DemoRefri.demoRefri.domain.ProductoDomain;
import com.DemoRefri.demoRefri.persistance.entities.Producto;
import com.DemoRefri.demoRefri.persistance.entities.repositories.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private S3Service s3Service; // 📌 Inyectamos el servicio de AWS S3

    @Override
    @Transactional
    public ProductoDomain crearProducto(ProductoDomain productoDomain, MultipartFile file) {
        // 📤 Subir imagen a S3 si existe
        String urlImagen = Optional.ofNullable(file)
                .map(f -> {
                    try {
                        return s3Service.subirImagen(f);
                    } catch (Exception e) {
                        throw new RuntimeException("Error al subir imagen", e);
                    }
                })
                .orElse("");

        productoDomain.setImagenUrl(urlImagen);  // Asociar imagen al producto

        Producto productoGuardado = productoRepository.save(productoDomain.toEntity());
        return ProductoDomain.fromEntity(productoGuardado);
    }

    @Override
    public ProductoDomain actualizarProducto(Integer id, ProductoDomain producto, MultipartFile file) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // 📤 Subir nueva imagen si el usuario la proporciona
        if (file != null && !file.isEmpty()) {
            try {
                String nuevaUrlImagen = s3Service.subirImagen(file);
                productoExistente.setImagenUrl(nuevaUrlImagen);
            } catch (Exception e) {
                throw new RuntimeException("Error al actualizar imagen", e);
            }
        }

        productoExistente.setNombre(producto.getNombre());
        productoExistente.setDescripcion(producto.getDescripcion());
        productoExistente.setPrecio(producto.getPrecio());
        productoExistente.setStock(producto.getStock());
        productoExistente.setCategoria(producto.getCategoria());

        Producto productoGuardado = productoRepository.save(productoExistente);

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