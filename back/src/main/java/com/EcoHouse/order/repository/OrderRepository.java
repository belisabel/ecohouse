package com.EcoHouse.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EcoHouse.order.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Buscar todas las órdenes por el ID del cliente
    List<Order> findByCustomerId(Long customerId);
    // Buscar una orden por su número de orden
    Optional<Order> findByOrderNumber(String orderNumber);
}
