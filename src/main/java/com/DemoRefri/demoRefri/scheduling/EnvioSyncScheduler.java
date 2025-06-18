package com.DemoRefri.demoRefri.scheduling;

import com.DemoRefri.demoRefri.enums.EstadoEnvio;
import com.DemoRefri.demoRefri.persistance.entities.Envio;
import com.DemoRefri.demoRefri.persistance.entities.repositories.EnvioRepository;
import com.DemoRefri.demoRefri.services.MercadoEnviosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnvioSyncScheduler {
    @Autowired
    private  EnvioRepository envioRepository;
    @Autowired
    private  MercadoEnviosService mercadoEnviosService;

    // 🔄 Sincroniza cada 4 horas
    @Scheduled(cron = "0 0 */4 * * *")
    public void sincronizarEnviosEnTransito() {
        log.info("⏳ Sincronizando envíos en tránsito con Mercado Envíos...");

        List<Envio> envios = envioRepository.findByEstadoEnvio(EstadoEnvio.EN_TRANSITO);

        for (Envio envio : envios) {
            if (envio.getTrackingNumber() != null && envio.getTrackingNumber().matches("\\d+")) {
                try {
                    Long shipmentId = Long.parseLong(envio.getTrackingNumber());
                    String resultado = mercadoEnviosService.sincronizarEstadoEnvio(shipmentId);
                    log.info("🚚 Envío {} actualizado: {}", envio.getId(), resultado);
                } catch (Exception e) {
                    log.error("❌ Error al sincronizar envío {}: {}", envio.getId(), e.getMessage());
                }
            }
        }

        log.info("✅ Sincronización completada");
    }
}