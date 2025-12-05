package com.EcoHouse.product.repository;

import com.EcoHouse.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Buscar productos activos
    List<Product> findByIsActiveTrue();

    // Buscar por categoría
    List<Product> findByCategoryId(Long categoryId);

    // Buscar por marca
    List<Product> findByBrandId(Long brandId);

    // Buscar por nombre (similar a un search)
    List<Product> findByNameContainingIgnoreCase(String name);
}
