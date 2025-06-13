package com.DemoRefri.demoRefri.services;

import com.DemoRefri.demoRefri.domain.PagoDomain;
import com.DemoRefri.demoRefri.domain.PedidoDomain;
import com.DemoRefri.demoRefri.enums.EstadoPago;
import com.DemoRefri.demoRefri.enums.EstadoPedido;
import com.DemoRefri.demoRefri.persistance.entities.Pago;
import com.DemoRefri.demoRefri.persistance.entities.Pedido;
import com.DemoRefri.demoRefri.persistance.entities.repositories.PagoRepository;
import com.DemoRefri.demoRefri.persistance.entities.repositories.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    @Transactional
    public PagoDomain registrarPago(Integer pedidoId, String mercadoPagoId) {
        if (mercadoPagoId == null || mercadoPagoId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de Mercado Pago no puede estar vacío.");
        }

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + pedidoId));

        if (pedido.getEstado().equals(EstadoPago.APROBADO)) {
            throw new IllegalStateException("El pedido ya ha sido pagado.");
        }

        PagoDomain pagoDomain = new PagoDomain(null, PedidoDomain.fromEntity(pedido), mercadoPagoId, EstadoPago.PENDIENTE, new Date());
        pagoRepository.save(pagoDomain.toEntity());

        return pagoDomain;
    }

    @Override
    @Transactional
    public PagoDomain actualizarEstadoPago(String mercadoPagoId, EstadoPago estado) {
        Pago pago = pagoRepository.findByMercadoPagoId(mercadoPagoId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + mercadoPagoId));

        pago.setEstado(estado);
        pagoRepository.save(pago);

        return PagoDomain.fromEntity(pago);
    }

    @Override
    public Optional<PagoDomain> obtenerPagoPorId(Integer id) {
        return pagoRepository.findById(id).map(PagoDomain::fromEntity);
    }

    @Transactional
    public void sincronizarPagoReal(String mercadoPagoId, String status, String externalReference) {
        EstadoPago estado = switch (status.toLowerCase()) {
            case "approved" -> EstadoPago.APROBADO;
            case "rejected" -> EstadoPago.RECHAZADO;
            default -> EstadoPago.PENDIENTE;
        };

        Optional<Pago> pagoOpt = pagoRepository.findByMercadoPagoId(mercadoPagoId);

        if (pagoOpt.isPresent()) {
            Pago pago = pagoOpt.get();
            pago.setEstado(estado);
            pagoRepository.save(pago);

            PedidoDomain pedido = PedidoDomain.fromEntity(pago.getPedido());
            pedido.setEstado(mapearEstadoPedidoDesdePago(estado));
            pedidoRepository.save(pedido.toEntity());

            System.out.println("✅ Pago y pedido sincronizados con estado: " + estado);
        } else {
            Integer pedidoId = extraerIdDesdeExternalReference(externalReference);
            Pedido pedido = pedidoRepository.findById(pedidoId)
                    .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + pedidoId));

            Pago nuevoPago = new Pago();
            nuevoPago.setMercadoPagoId(mercadoPagoId);
            nuevoPago.setEstado(estado);
            nuevoPago.setFecha(new Date());
            nuevoPago.setPedido(pedido);

            pedido.setEstado(mapearEstadoPedidoDesdePago(estado));

            pagoRepository.save(nuevoPago);
            pedidoRepository.save(pedido);

            System.out.println("🆕 Nuevo pago vinculado al pedido #" + pedidoId + " con estado: " + estado);
        }
    }

    private Integer extraerIdDesdeExternalReference(String ref) {
        try {
            String idStr = ref.replaceAll("[^0-9]", ""); // Extrae solo números del `external_reference`
            return Integer.parseInt(idStr);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo extraer el ID del pedido desde external_reference: " + ref);
        }
    }

    public EstadoPedido mapearEstadoPedidoDesdePago(EstadoPago estadoPago) {
        return switch (estadoPago) {
            case APROBADO -> EstadoPedido.PAGADO;
            case RECHAZADO -> EstadoPedido.CANCELADO;
            default -> EstadoPedido.PENDIENTE;
        };
    }
}