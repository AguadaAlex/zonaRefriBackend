package com.DemoRefri.demoRefri.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagoRequest {
    @NotNull(message = "El pedidoId no puede ser nulo")
    private Integer pedidoId;

    private String mercadoPagoId;

    @NotNull(message = "El precio no puede ser nulo.")
    private BigDecimal precio; // ✅ Agregar precio

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getMercadoPagoId() {
        return mercadoPagoId;
    }

    public void setMercadoPagoId(String mercadoPagoId) {
        this.mercadoPagoId = mercadoPagoId;
    }
}