package com.DemoRefri.demoRefri.services;

import com.DemoRefri.demoRefri.domain.PedidoDomain;
import com.DemoRefri.demoRefri.domain.UsuarioDomain;
import com.DemoRefri.demoRefri.dto.PedidoDetalleRequest;
import com.DemoRefri.demoRefri.enums.EstadoPedido;
import com.DemoRefri.demoRefri.persistance.entities.Pedido;
import com.DemoRefri.demoRefri.persistance.entities.PedidoDetalle;
import com.DemoRefri.demoRefri.persistance.entities.Producto;
import com.DemoRefri.demoRefri.persistance.entities.Usuario;
import com.DemoRefri.demoRefri.persistance.entities.repositories.PedidoDetalleRepository;
import com.DemoRefri.demoRefri.persistance.entities.repositories.PedidoRepository;
import com.DemoRefri.demoRefri.persistance.entities.repositories.ProductoRepository;
import com.DemoRefri.demoRefri.persistance.entities.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PedidoDetalleRepository pedidoDetalleRepository;
    @Autowired
    private ProductoRepository productoRepository;


    public PedidoServiceImpl(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository, PedidoDetalleRepository pedidoDetalleRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.pedidoDetalleRepository = pedidoDetalleRepository;
    }
    public PedidoServiceImpl() {
        this.pedidoRepository = null; // Evita errores de inicialización
    }

    @Override
    public PedidoDomain crearPedido(Integer usuarioId, List<PedidoDetalleRequest> detallesRequest) {
        System.out.println("Usuario ID: " + usuarioId);
        System.out.println("Lista Detalle Request: " + detallesRequest);

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        BigDecimal total = BigDecimal.ZERO;

        for (PedidoDetalleRequest detalleReq : detallesRequest) {
            if (detalleReq.getPrecioUnitario() == null) {
                throw new IllegalArgumentException("El precio unitario no puede ser nulo para el producto: " + detalleReq.getProductoId());
            }

            total = total.add(detalleReq.getPrecioUnitario().multiply(BigDecimal.valueOf(detalleReq.getCantidad())));
        }

        PedidoDomain pedidoDomain = new PedidoDomain(null, UsuarioDomain.fromEntity(usuario), new Date(), total, EstadoPedido.PENDIENTE);
        Pedido pedidoEntity = pedidoRepository.save(pedidoDomain.toEntity());

        for (PedidoDetalleRequest detalleReq : detallesRequest) {
            Producto producto = productoRepository.findById(detalleReq.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalleReq.getProductoId()));

            PedidoDetalle detalle = new PedidoDetalle();
            detalle.setProducto(producto);
            detalle.setCantidad(detalleReq.getCantidad());
            detalle.setPrecioUnitario(detalleReq.getPrecioUnitario());
            detalle.setPedido(pedidoEntity);

            pedidoDetalleRepository.save(detalle);
        }

        return PedidoDomain.fromEntity(pedidoEntity);
    }

    @Override
    public List<PedidoDomain> obtenerPedidosPorUsuario(Integer usuarioId) {
        // Obtener pedidos directamente sin cargar el usuario primero
        List<Pedido> pedidos = pedidoRepository.findByUsuarioId(usuarioId);

        // Convertir lista de `Pedido` a lista de `PedidoDomain`
        return pedidos.stream()
                .map(PedidoDomain::fromEntity)
                .toList();
    }
    @Override
    public PedidoDomain actualizarEstadoPedido(Integer pedidoId, EstadoPedido estado) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        pedido.setEstado(estado);
        return PedidoDomain.fromEntity(pedidoRepository.save(pedido));
    }
}