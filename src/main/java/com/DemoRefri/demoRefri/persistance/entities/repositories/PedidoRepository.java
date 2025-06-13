package com.DemoRefri.demoRefri.persistance.entities.repositories;

import com.DemoRefri.demoRefri.persistance.entities.Pedido;
import com.DemoRefri.demoRefri.persistance.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    Optional<Pedido> findById(Integer id);
    List<Pedido> findByUsuario(Usuario usuario);

    List<Pedido> findByUsuarioId(Integer usuarioId);
}