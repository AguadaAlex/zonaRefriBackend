package com.DemoRefri.demoRefri.controllers;

import com.DemoRefri.demoRefri.domain.EnvioDomain;
import com.DemoRefri.demoRefri.enums.EstadoEnvio;
import com.DemoRefri.demoRefri.services.EnvioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/envios")
@RequiredArgsConstructor
public class EnvioController {
    @Autowired
    private EnvioService envioService;

    // ✅ Crear nuevo envío para un pedido
    @PostMapping("/crear")
    public ResponseEntity<EnvioDomain> crearEnvio(@RequestParam Integer pedidoId,
                                                  @RequestParam String direccion,
                                                  @RequestParam String codigoPostal,
                                                  @RequestParam String ciudad) {
        EnvioDomain nuevoEnvio = envioService.crearEnvio(pedidoId, direccion, codigoPostal, ciudad);
        return ResponseEntity.ok(nuevoEnvio);
    }

    // ✅ Actualizar estado de un envío
    @PutMapping("/{envioId}/estado")
    public ResponseEntity<EnvioDomain> actualizarEstado(@PathVariable Integer envioId,
                                                        @RequestParam EstadoEnvio nuevoEstado) {
        EnvioDomain actualizado = envioService.actualizarEstadoEnvio(envioId, nuevoEstado);
        return ResponseEntity.ok(actualizado);
    }

    // ✅ Consultar envío por tracking number
    @GetMapping("/tracking/{codigo}")
    public ResponseEntity<EnvioDomain> obtenerPorTracking(@PathVariable String codigo) {
        return envioService.obtenerEnvioPorTracking(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Listar envíos de un pedido
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<EnvioDomain>> listarPorPedido(@PathVariable Integer pedidoId) {
        List<EnvioDomain> lista = envioService.obtenerEnviosPorPedido(pedidoId);
        return ResponseEntity.ok(lista);
    }
}