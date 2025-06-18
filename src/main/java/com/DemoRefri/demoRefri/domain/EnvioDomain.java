package com.DemoRefri.demoRefri.domain;

import com.DemoRefri.demoRefri.enums.EstadoEnvio;
import com.DemoRefri.demoRefri.persistance.entities.Envio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioDomain {
    private Integer id;
    private PedidoDomain pedido;
    private String direccion;
    private String codigoPostal;
    private String ciudad;
    private EstadoEnvio estadoEnvio;
    private String trackingNumber;
    private Date fechaCreacion;
    private Date fechaActualizacion;

    // ✅ Método para convertir una entidad Envio en un EnvioDomain
    public static EnvioDomain fromEntity(Envio envio) {
        return new EnvioDomain(
                envio.getId(),
                PedidoDomain.fromEntity(envio.getPedido()), // 👈 Conversión de Pedido también
                envio.getDireccion(),
                envio.getCodigoPostal(),
                envio.getCiudad(),
                envio.getEstadoEnvio(),
                envio.getTrackingNumber(),
                envio.getFechaCreacion(),
                envio.getFechaActualizacion()
        );
    }

    // ✅ Método para convertir un EnvioDomain en una entidad Envio
    public Envio toEntity() {
        Envio envio = new Envio();
        envio.setId(this.id);
        envio.setPedido(this.pedido.toEntity());
        envio.setDireccion(this.direccion);
        envio.setCodigoPostal(this.codigoPostal);
        envio.setCiudad(this.ciudad);
        envio.setEstadoEnvio(this.estadoEnvio);
        envio.setTrackingNumber(this.trackingNumber);
        envio.setFechaCreacion(this.fechaCreacion);
        envio.setFechaActualizacion(this.fechaActualizacion);
        return envio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PedidoDomain getPedido() {
        return pedido;
    }

    public void setPedido(PedidoDomain pedido) {
        this.pedido = pedido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public EstadoEnvio getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(EstadoEnvio estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}