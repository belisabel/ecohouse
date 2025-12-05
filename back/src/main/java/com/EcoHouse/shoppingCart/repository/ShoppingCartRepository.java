package com.EcoHouse.shoppingCart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EcoHouse.shoppingCart.model.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    // Buscar el carrito de compras por el ID del cliente
    Optional<ShoppingCart> findByCustomerId(Long customerId);
}
