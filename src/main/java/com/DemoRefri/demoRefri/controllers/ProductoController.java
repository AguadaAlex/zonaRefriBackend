package com.DemoRefri.demoRefri.controllers;

import com.DemoRefri.demoRefri.domain.ProductoDomain;
import com.DemoRefri.demoRefri.dto.ProductoRequest;
import com.DemoRefri.demoRefri.dto.ProductoResponse;
import com.DemoRefri.demoRefri.services.ProductoServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    @Autowired
    private ProductoServiceImpl productoService;

    @GetMapping
    public List<ProductoResponse> listarProductos() {
        return productoService.obtenerTodosLosProductos()
                .stream()
                .map(producto -> new ProductoResponse(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getDescripcion(),
                        producto.getPrecio(),
                        producto.getStock()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public ProductoResponse obtenerProducto(@PathVariable Integer id) {
        ProductoDomain producto = productoService.obtenerProductoPorId(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock()
        );
    }

    @PostMapping
    public List<ProductoResponse> crearProductos(@Valid @RequestBody List<ProductoRequest> productosRequest) {
        List<ProductoDomain> productosGuardados = productosRequest.stream()
                .map(productoRequest -> productoService.crearProducto(
                        new ProductoDomain(
                                null,
                                productoRequest.getNombre(),
                                productoRequest.getDescripcion(),
                                productoRequest.getPrecio(),
                                productoRequest.getStock(),
                                "", // Imagen vacía en lugar de null
                                "Sin categoría", // Categoría por defecto
                                new Date() // Fecha de creación automática

                        )
                ))
                .toList();

        productosGuardados.forEach(producto -> System.out.println("Producto guardado: " + producto)); // 📌 Depuración

        return productosGuardados.stream()
                .map(producto -> new ProductoResponse(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getDescripcion(),
                        producto.getPrecio(),
                        producto.getStock()
                ))
                .toList();
    }

    @PutMapping("/{id}")
    public ProductoResponse actualizarProducto(@PathVariable Integer id, @Valid @RequestBody ProductoRequest productoRequest) {
        ProductoDomain productoActualizado = productoService.actualizarProducto(
                id,
                new ProductoDomain(
                        id,
                        productoRequest.getNombre(),
                        productoRequest.getDescripcion(),
                        productoRequest.getPrecio(),
                        productoRequest.getStock(),
                        "", // Imagen vacía en lugar de null
                        "Sin categoría", // Categoría por defecto
                        new Date() // Fecha de creación automática

                )
        );
        System.out.println("Producto actualizado en servicio: " + productoActualizado); // 📌 Depuración

        return new ProductoResponse(
                productoActualizado.getId(),
                productoActualizado.getNombre(),
                productoActualizado.getDescripcion(),
                productoActualizado.getPrecio(),
                productoActualizado.getStock()
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Integer id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.ok("Producto eliminado correctamente.");
    }
}