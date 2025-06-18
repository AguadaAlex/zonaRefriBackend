package com.DemoRefri.demoRefri.persistance.entities.repositories;

import com.DemoRefri.demoRefri.persistance.entities.Envio;
import com.DemoRefri.demoRefri.enums.EstadoEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Integer> {

    // ✅ Buscar un envío por su número de tracking
    Optional<Envio> findByTrackingNumber(String trackingNumber);

    // ✅ Obtener todos los envíos de un pedido específico
    List<Envio> findByPedidoId(Integer pedidoId);

    // ✅ Obtener envíos por estado (Ej: EN_TRANSITO, ENTREGADO)
    List<Envio> findByEstadoEnvio(EstadoEnvio estadoEnvio);
}