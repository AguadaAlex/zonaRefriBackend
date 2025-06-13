package com.DemoRefri.demoRefri.controllers;

import com.DemoRefri.demoRefri.dto.PagoRequest;
import com.DemoRefri.demoRefri.services.MercadoPagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mercadopago")
@RequiredArgsConstructor
@CrossOrigin // ✅ Agregado para evitar bloqueos por políticas de CORS
public class MercadoPagoController {

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @PostMapping("/crear-preferencia")
    public ResponseEntity<String> crearPreferencia(@Valid @RequestBody PagoRequest pagoRequest) {
        String preferenceUrl = mercadoPagoService.crearPreferenciaPago(
                "Pago Pedido " + pagoRequest.getPedidoId(),
                pagoRequest.getPrecio(),
                "pedido-" + pagoRequest.getPedidoId() // ✅ external_reference agregado
        );
        return ResponseEntity.ok(preferenceUrl != null ? preferenceUrl : "Error al generar preferencia de pago.");
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> recibirNotificacion(@RequestBody(required = false) Map<String, Object> payload) {
        if (payload == null) {
            System.err.println("❌ Webhook recibido sin datos.");
            return ResponseEntity.badRequest().body("Webhook sin contenido.");
        }

        System.out.println("🔄 Webhook recibido: " + payload);
        mercadoPagoService.procesarNotificacion(payload);
        return ResponseEntity.ok("Notificación recibida.");
    }


    @GetMapping("/simular-pago/{paymentId}/{status}")
    public ResponseEntity<Map<String, Object>> simularPago(@PathVariable String paymentId, @PathVariable String status) {
        Map<String, Object> pagoSimulado = mercadoPagoService.simularPago(paymentId, status);
        return ResponseEntity.ok(pagoSimulado);
    }
}