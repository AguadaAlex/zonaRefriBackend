package com.DemoRefri.demoRefri.services;

import com.DemoRefri.demoRefri.dto.MercadoEnvioDTO;
import com.DemoRefri.demoRefri.enums.EstadoEnvio;
import com.DemoRefri.demoRefri.persistance.entities.Envio;
import com.DemoRefri.demoRefri.persistance.entities.repositories.EnvioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MercadoEnviosService {

    @Value("${mercadopago.access.token}")
    private String accessToken;
    @Autowired
    private  EnvioRepository envioRepository;
    @Value("${mercado.envios.api.url}")
    private String apiUrl;


    public String sincronizarEstadoEnvio(Long shipmentId) {
        try {
            // Crear headers con autorización
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            // Hacer la solicitud
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl + shipmentId,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();

                // Mapear JSON a DTO
                ObjectMapper mapper = new ObjectMapper();
                MercadoEnvioDTO envioDTO = mapper.readValue(responseBody, MercadoEnvioDTO.class);

                // Buscar envío por tracking
                Optional<Envio> envioOpt = envioRepository.findByTrackingNumber(envioDTO.getTracking_number());

                if (envioOpt.isPresent()) {
                    Envio envio = envioOpt.get();

                    switch (envioDTO.getStatus()) {
                        case "shipped" -> envio.setEstadoEnvio(EstadoEnvio.EN_TRANSITO);
                        case "delivered" -> envio.setEstadoEnvio(EstadoEnvio.ENTREGADO);
                        case "not_delivered" -> envio.setEstadoEnvio(EstadoEnvio.PENDIENTE);
                        case "cancelled" -> envio.setEstadoEnvio(EstadoEnvio.CANCELADO);
                        default -> System.out.println("ℹ️ Estado no reconocido: " + envioDTO.getStatus());
                    }

                    envio.setFechaActualizacion(new Date());
                    envioRepository.save(envio);
                    return "✅ Envío actualizado: " + envio.getEstadoEnvio();
                } else {
                    return "⚠️ No se encontró un envío con tracking: " + envioDTO.getTracking_number();
                }

            } else {
                return "❌ Error consultando Mercado Envíos: " + response.getStatusCode();
            }

        } catch (Exception e) {
            return "🚨 Error procesando envío: " + e.getMessage();
        }
    }
}