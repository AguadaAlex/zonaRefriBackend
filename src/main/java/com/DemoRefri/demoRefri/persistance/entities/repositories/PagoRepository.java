package com.DemoRefri.demoRefri.persistance.entities.repositories;

import com.DemoRefri.demoRefri.persistance.entities.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    Optional<Pago> findByMercadoPagoId(String mercadoPagoId);
}