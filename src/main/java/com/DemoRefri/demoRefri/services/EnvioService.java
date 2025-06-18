package com.DemoRefri.demoRefri.services;

import com.DemoRefri.demoRefri.domain.EnvioDomain;
import com.DemoRefri.demoRefri.enums.EstadoEnvio;

import java.util.List;
import java.util.Optional;

public interface EnvioService {

    // ✅ Registrar un nuevo envío para un pedido específico
    EnvioDomain crearEnvio(Integer pedidoId, String direccion, String codigoPostal, String ciudad);

    // ✅ Actualizar el estado del envío (Ejemplo: PENDIENTE → EN_TRANSITO)
    EnvioDomain actualizarEstadoEnvio(Integer envioId, EstadoEnvio estadoEnvio);

    // ✅ Buscar un envío por su número de tracking
    Optional<EnvioDomain> obtenerEnvioPorTracking(String trackingNumber);

    // ✅ Obtener todos los envíos asociados a un pedido
    List<EnvioDomain> obtenerEnviosPorPedido(Integer pedidoId);
}