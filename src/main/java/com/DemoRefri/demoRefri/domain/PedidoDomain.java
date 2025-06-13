package com.DemoRefri.demoRefri.domain;

import com.DemoRefri.demoRefri.enums.EstadoPedido;
import com.DemoRefri.demoRefri.persistance.entities.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PedidoDomain {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UsuarioDomain getUsuarioDomain() {
        return usuarioDomain;
    }

    public void setUsuarioDomain(UsuarioDomain usuarioDomain) {
        this.usuarioDomain = usuarioDomain;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    private Integer id;
    private UsuarioDomain usuarioDomain;
    private Date fecha;
    private BigDecimal total;
    private EstadoPedido estado;

    public PedidoDomain(Integer id, UsuarioDomain usuarioDomain, Date fecha, BigDecimal total, EstadoPedido estado) {
        this.id=id;
        this.usuarioDomain=usuarioDomain;
        this.fecha=fecha;
        this.total=total;
        this.estado=estado;
    }

    // Método estático para convertir de Entity a Domain
    public static PedidoDomain fromEntity(Pedido pedido) {
        if (pedido == null) {
            return null; // Evita errores de conversión con objetos null
        }
        return new PedidoDomain(
                pedido.getId(),
                UsuarioDomain.fromEntity(pedido.getUsuario()), // Conversión interna
                pedido.getFecha(),
                pedido.getTotal(),
                pedido.getEstado()
        );
    }

    // Método para convertir de Domain a Entity
    public Pedido toEntity() {
        Pedido pedido = new Pedido();
        pedido.setId(this.id);

        // Convertir `UsuarioDomain` a `Usuario` antes de asignarlo
        if (this.usuarioDomain != null) {
            pedido.setUsuario(this.usuarioDomain.toEntity());
        }

        pedido.setFecha(this.fecha);
        pedido.setTotal(this.total);
        pedido.setEstado(this.estado);

        return pedido;
    }

    // Método de validación para asegurar datos válidos antes de la conversión
    public void validarPedido() {
        if (this.fecha == null) {
            throw new IllegalArgumentException("La fecha del pedido no puede ser nula.");
        }
        if (this.usuarioDomain == null) {
            throw new IllegalArgumentException("El usuario del pedido no puede ser nulo.");
        }
        if (this.total == null || this.total.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El total del pedido debe ser mayor a 0.");
        }
    }

    // Método de actualización de estado
    public void actualizarEstado(EstadoPedido nuevoEstado) {
        this.estado = nuevoEstado;
    }

    // Método auxiliar para mostrar información del pedido
    @Override
    public String toString() {
        return "PedidoDomain{" +
                "id=" + id +
                ", usuario=" + usuarioDomain +
                ", fecha=" + fecha +
                ", total=" + total +
                ", estado=" + estado +
                '}';
    }
}