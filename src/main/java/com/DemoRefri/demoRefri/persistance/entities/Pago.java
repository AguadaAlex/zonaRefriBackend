package com.DemoRefri.demoRefri.persistance.entities;

import com.DemoRefri.demoRefri.enums.EstadoPago;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    public Integer getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public String getMercadoPagoId() {
        return mercadoPagoId;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public Date getFecha() {
        return fecha;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(nullable = false, unique = true, length = 100)
    private String mercadoPagoId; // ID de transacción en Mercado Pago

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPago estado;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;


    public void setId(Integer id) {
        this.id = id;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setMercadoPagoId(String mercadoPagoId) {
        this.mercadoPagoId = mercadoPagoId;
    }

    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}