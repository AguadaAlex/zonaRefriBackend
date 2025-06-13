package com.DemoRefri.demoRefri.persistance.entities.repositories;

import com.DemoRefri.demoRefri.persistance.entities.Pedido;
import com.DemoRefri.demoRefri.persistance.entities.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Integer> {
    List<PedidoDetalle> findByPedido(Pedido pedido);
}