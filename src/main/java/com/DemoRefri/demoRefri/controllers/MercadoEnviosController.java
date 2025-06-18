package com.DemoRefri.demoRefri.controllers;

import com.DemoRefri.demoRefri.services.MercadoEnviosService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mercado-envios")
@RequiredArgsConstructor
public class MercadoEnviosController {
    @Autowired
    private MercadoEnviosService mercadoEnviosService;

    // ✅ Consultar y sincronizar el estado del envío desde Mercado Libre
    @GetMapping("/estado/{shipmentId}")
    public ResponseEntity<String> consultarEstadoEnvio(@PathVariable Long shipmentId) {
        String resultado = mercadoEnviosService.sincronizarEstadoEnvio(shipmentId);
        return ResponseEntity.ok(resultado);
    }

    // Aquí podés agregar más endpoints si querés manejar etiquetas, cancelaciones, etc.
}