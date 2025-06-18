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
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
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
                        producto.getStock(),
                        producto.getImagenUrl()
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
                producto.getStock(),
                producto.getImagenUrl()
        );
    }

    @PostMapping("/crear")
    public ResponseEntity<ProductoResponse> crearProducto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") BigDecimal precio,
            @RequestParam("stock") Integer stock) throws Exception {

        ProductoDomain producto = new ProductoDomain(null, nombre, descripcion, precio, stock, "", "Sin categoría", new Date());

        // 📌 El servicio se encarga de la subida de la imagen a S3 y la creación del producto
        ProductoDomain productoGuardado = productoService.crearProducto(producto, file);

        return ResponseEntity.ok(new ProductoResponse(
                productoGuardado.getId(),
                productoGuardado.getNombre(),
                productoGuardado.getDescripcion(),
                productoGuardado.getPrecio(),
                productoGuardado.getStock(),
                productoGuardado.getImagenUrl()
        ));
    }

    @PutMapping("/{id}")
    public ProductoResponse actualizarProducto(
            @PathVariable Integer id,
            @Valid @RequestBody ProductoRequest productoRequest,
            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        ProductoDomain producto = new ProductoDomain(id, productoRequest.getNombre(), productoRequest.getDescripcion(),
                productoRequest.getPrecio(), productoRequest.getStock(), "", "Sin categoría", new Date());

        ProductoDomain productoActualizado = productoService.actualizarProducto(id, producto, file);

        return new ProductoResponse(
                productoActualizado.getId(),
                productoActualizado.getNombre(),
                productoActualizado.getDescripcion(),
                productoActualizado.getPrecio(),
                productoActualizado.getStock(),
                productoActualizado.getImagenUrl()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Integer id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.ok("Producto eliminado correctamente.");
    }
}