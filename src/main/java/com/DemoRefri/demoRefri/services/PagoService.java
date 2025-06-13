package com.DemoRefri.demoRefri.services;

import com.DemoRefri.demoRefri.domain.PagoDomain;
import com.DemoRefri.demoRefri.enums.EstadoPago;

import java.util.Optional;

public interface PagoService {
    PagoDomain registrarPago(Integer pedidoId, String mercadoPagoId);
    PagoDomain actualizarEstadoPago(String mercadoPagoId, EstadoPago estado);
    Optional<PagoDomain> obtenerPagoPorId(Integer id);
}