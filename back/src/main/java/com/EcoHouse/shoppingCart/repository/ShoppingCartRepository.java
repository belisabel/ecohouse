package com.EcoHouse.shoppingCart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.EcoHouse.shoppingCart.model.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    // Query básica sin JOIN FETCH (puede causar LazyInitializationException)
    Optional<ShoppingCart> findByCustomerId(Long customerId);

    // Query con JOIN FETCH para cargar items y productos en una sola consulta
    // Esto evita LazyInitializationException al acceder a los productos
    @Query("SELECT sc FROM ShoppingCart sc " +
           "LEFT JOIN FETCH sc.items items " +
           "LEFT JOIN FETCH items.product " +
           "WHERE sc.customer.id = :customerId")
    Optional<ShoppingCart> findByCustomerIdWithItems(@Param("customerId") Long customerId);
}
