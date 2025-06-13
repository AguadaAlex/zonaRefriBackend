package com.DemoRefri.demoRefri.domain;

import com.DemoRefri.demoRefri.enums.EstadoPago;
import com.DemoRefri.demoRefri.persistance.entities.Pago;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data

public class PagoDomain {
    private Integer id;
    private PedidoDomain pedidoDomain;
    private String mercadoPagoId; // ID de transacción en Mercado Pago
    private EstadoPago estado;
    private Date fecha;

    public PagoDomain(Integer id, PedidoDomain pedidoDomain, String mercadoPagoId, EstadoPago estado, Date fecha) {
        this.id=id;
        this.pedidoDomain=pedidoDomain;
        this.mercadoPagoId=mercadoPagoId;
        this.estado=estado;
        this.fecha=fecha;
    }

    // Método estático para convertir de Entity a Domain
    public static PagoDomain fromEntity(Pago pago) {
        if (pago == null) {
            return null; // Evita errores con valores nulos
        }
        return new PagoDomain(
                pago.getId(),
                PedidoDomain.fromEntity(pago.getPedido()), // Conversión interna de Pedido
                pago.getMercadoPagoId(),
                pago.getEstado(),
                pago.getFecha()
        );
    }

    // Método para convertir de Domain a Entity
    public Pago toEntity() {
        Pago pago = new Pago();
        pago.setId(this.id);

        // Convertir `PedidoDomain` a `Pedido` antes de asignarlo
        if (this.pedidoDomain != null) {
            pago.setPedido(this.pedidoDomain.toEntity());
        }

        pago.setMercadoPagoId(this.mercadoPagoId);
        pago.setEstado(this.estado);
        pago.setFecha(this.fecha);

        return pago;
    }
}