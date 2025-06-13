package com.DemoRefri.demoRefri.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.DemoRefri.demoRefri.persistance.entities.PedidoDetalle;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoRequest {
    @NotNull(message = "El usuarioId no puede ser nulo")
    private Integer usuarioId;

    @NotNull(message = "Los detalles del pedido no pueden ser nulos")
    @Size(min = 1, message = "Debe haber al menos un producto en el pedido")
    private List<PedidoDetalleRequest> detalles;

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<PedidoDetalleRequest> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<PedidoDetalleRequest> detalles) {
        this.detalles = detalles;
    }
}