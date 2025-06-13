package com.DemoRefri.demoRefri.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class PedidoDetalleRequest {
    @NotNull
    private Integer productoId;

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    @NotNull
    private Integer cantidad;
    private BigDecimal precioUnitario = BigDecimal.ZERO; // ✅ Valor por defecto para evitar `null`
}
