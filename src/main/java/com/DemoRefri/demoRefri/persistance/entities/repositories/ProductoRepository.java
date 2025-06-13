package com.DemoRefri.demoRefri.persistance.entities.repositories;

import com.DemoRefri.demoRefri.persistance.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByCategoria(String categoria);
}