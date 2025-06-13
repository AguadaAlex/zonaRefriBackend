package com.DemoRefri.demoRefri.controllers;

import com.DemoRefri.demoRefri.domain.PagoDomain;
import com.DemoRefri.demoRefri.dto.PagoRequest;
import com.DemoRefri.demoRefri.enums.EstadoPago;
import com.DemoRefri.demoRefri.services.PagoServiceImpl;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {
    @Autowired
    private PagoServiceImpl pagoService;

    @PostMapping("/procesar")
    public PagoDomain procesarPago(@Valid @RequestBody PagoRequest pagoRequest) {
         String mercadoPagoId; // ✅ Usa String en lugar de Long
        try {
            mercadoPagoId = pagoRequest.getMercadoPagoId(); // ✅ Convertir String a Long
        } catch (NumberFormatException e) {
            throw new RuntimeException("El ID de Mercado Pago debe ser un número válido.");
        }
        return pagoService.registrarPago(pagoRequest.getPedidoId(), mercadoPagoId);
    }
    @PutMapping("/{mercadoPagoId}/estado")
    public ResponseEntity<String> actualizarEstadoPago(@PathVariable String mercadoPagoId, @RequestParam String estado) {
        try {
            EstadoPago estadoPago = EstadoPago.valueOf(estado.toUpperCase());
            pagoService.actualizarEstadoPago(mercadoPagoId, estadoPago);
            return ResponseEntity.ok("Estado actualizado correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Estado inválido: " + estado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el estado.");
        }
    }
}