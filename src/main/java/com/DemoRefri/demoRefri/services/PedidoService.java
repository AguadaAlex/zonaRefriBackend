package com.DemoRefri.demoRefri.services;

import com.DemoRefri.demoRefri.dto.PedidoDetalleRequest;
import com.DemoRefri.demoRefri.enums.EstadoPedido;
import com.DemoRefri.demoRefri.domain.PedidoDomain;
import com.DemoRefri.demoRefri.persistance.entities.Pedido;
import com.DemoRefri.demoRefri.persistance.entities.PedidoDetalle;

import java.util.List;

public interface PedidoService {
    PedidoDomain crearPedido(Integer usuarioId, List<PedidoDetalleRequest> detalles);
    List<PedidoDomain> obtenerPedidosPorUsuario(Integer usuarioId);
    PedidoDomain actualizarEstadoPedido(Integer pedidoId, EstadoPedido estado);
}