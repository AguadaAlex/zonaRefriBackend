package com.DemoRefri.demoRefri.controllers;

import com.DemoRefri.demoRefri.domain.PedidoDomain;
import com.DemoRefri.demoRefri.dto.PedidoRequest;
import com.DemoRefri.demoRefri.dto.PedidoDetalleRequest;
import com.DemoRefri.demoRefri.enums.EstadoPedido;
import com.DemoRefri.demoRefri.persistance.entities.PedidoDetalle;
import com.DemoRefri.demoRefri.persistance.entities.Producto;
import com.DemoRefri.demoRefri.services.PedidoServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    @Autowired
    private PedidoServiceImpl pedidoService;

    @GetMapping("/{usuarioId}")
    public List<PedidoDomain> obtenerPedidosPorUsuario(@PathVariable Integer usuarioId) {
        return pedidoService.obtenerPedidosPorUsuario(usuarioId);
    }

    @PostMapping
    public PedidoDomain crearPedido(@Valid @RequestBody PedidoRequest pedidoRequest) {
        List<PedidoDetalleRequest> detallesRequest = pedidoRequest.getDetalles();

        return pedidoService.crearPedido(pedidoRequest.getUsuarioId(), detallesRequest);
    }

    @PutMapping("/{id}/estado")
    public PedidoDomain actualizarEstadoPedido(@PathVariable Integer id, @RequestParam String estado) {
        EstadoPedido estadoPedido = EstadoPedido.valueOf(estado.toUpperCase());
        return pedidoService.actualizarEstadoPedido(id, estadoPedido);
    }

    @GetMapping("/verificar-roles")
    public ResponseEntity<String> verificarRoles(Authentication authentication) {
        System.out.println("Roles en el token: " + authentication.getAuthorities());
        return ResponseEntity.ok("Roles verificados en logs.");
    }
}