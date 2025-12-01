package com.EcoHouse.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EcoHouse.order.model.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Obtener los ítems asociados a una orden
    List<OrderItem> findByOrderId(Long orderId);

    // Obtener todos los ítems de un producto específico (opcional)
    List<OrderItem> findByProductId(Long productId);
}
