package com.DemoRefri.demoRefri.services;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MercadoPagoService {

    @Autowired
    private PagoServiceImpl pagoService;

    @Value("${mercadopago.access.token}")
    private String accessToken;

    public String crearPreferenciaPago(String titulo, BigDecimal precio, String externalReference) {
        try {
            MercadoPagoConfig.setAccessToken(accessToken);

            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title(titulo)
                    .quantity(1)
                    .unitPrice(precio)
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(List.of(item))
                    .externalReference(externalReference) // ✅ Se guarda el ID del pedido
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            System.out.println("🔗 Preferencia creada con External Reference: " + externalReference);
            return preference.getInitPoint();
        } catch (MPApiException e) {
            System.err.println("Error en la API de Mercado Pago: " + e.getMessage());
            return null;
        } catch (MPException e) {
            System.err.println("Error en Mercado Pago SDK: " + e.getMessage());
            return null;
        }
    }

    public void procesarNotificacion(Map<String, Object> payload) {
        try {
            MercadoPagoConfig.setAccessToken(accessToken);
            Map<String, Object> data = (Map<String, Object>) payload.get("data");

            if (data != null && data.containsKey("id")) {
                String paymentId = String.valueOf(data.get("id"));
                PaymentClient paymentClient = new PaymentClient();
                Payment payment = paymentClient.get(Long.valueOf(paymentId));

                pagoService.sincronizarPagoReal(
                        String.valueOf(payment.getId()),
                        payment.getStatus(),
                        payment.getExternalReference()
                );
            }
        } catch (Exception e) {
            System.err.println("🚨 Error al procesar la notificación de Mercado Pago: " + e.getMessage());
        }
    }
    public Map<String, Object> simularPago(String paymentId, String status) {
        Map<String, Object> pagoSimulado = new HashMap<>();
        pagoSimulado.put("id", paymentId);
        pagoSimulado.put("status", status);
        pagoSimulado.put("status_detail", status.equals("approved") ? "accredited" : "pending");

        System.out.println("Simulación de pago: " + paymentId);
        System.out.println("Estado simulado: " + pagoSimulado.get("status"));

        return pagoSimulado;
    }
}