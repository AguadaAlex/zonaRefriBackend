package com.DemoRefri.demoRefri.services;

import com.DemoRefri.demoRefri.domain.EnvioDomain;
import com.DemoRefri.demoRefri.enums.EstadoEnvio;
import com.DemoRefri.demoRefri.persistance.entities.Envio;
import com.DemoRefri.demoRefri.persistance.entities.Pedido;
import com.DemoRefri.demoRefri.persistance.entities.repositories.EnvioRepository;
import com.DemoRefri.demoRefri.persistance.entities.repositories.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnvioServiceImpl implements EnvioService {
    @Autowired
    private EnvioRepository envioRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public EnvioDomain crearEnvio(Integer pedidoId, String direccion, String codigoPostal, String ciudad) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + pedidoId));

        Envio envio = new Envio();
        envio.setPedido(pedido);
        envio.setDireccion(direccion);
        envio.setCodigoPostal(codigoPostal);
        envio.setCiudad(ciudad);
        envio.setEstadoEnvio(EstadoEnvio.PENDIENTE);
        envio.setFechaCreacion(new Date());
        envio.setFechaActualizacion(new Date());

        Envio guardado = envioRepository.save(envio);
        return EnvioDomain.fromEntity(guardado);
    }

    @Override
    public EnvioDomain actualizarEstadoEnvio(Integer envioId, EstadoEnvio estadoEnvio) {
        Envio envio = envioRepository.findById(envioId)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado con ID: " + envioId));

        envio.setEstadoEnvio(estadoEnvio);
        envio.setFechaActualizacion(new Date());
        envioRepository.save(envio);

        return EnvioDomain.fromEntity(envio);
    }

    @Override
    public Optional<EnvioDomain> obtenerEnvioPorTracking(String trackingNumber) {
        return envioRepository.findByTrackingNumber(trackingNumber)
                .map(EnvioDomain::fromEntity);
    }

    @Override
    public List<EnvioDomain> obtenerEnviosPorPedido(Integer pedidoId) {
        return envioRepository.findByPedidoId(pedidoId).stream()
                .map(EnvioDomain::fromEntity)
                .collect(Collectors.toList());
    }
}