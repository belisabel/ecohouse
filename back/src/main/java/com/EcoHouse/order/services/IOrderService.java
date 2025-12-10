package com.EcoHouse.order.services;

import java.util.List;

import com.EcoHouse.order.model.Order;
import com.EcoHouse.order.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService {

    // Crear una nueva orden
    Order createOrder(Long customerId);

    // Obtener una orden por su ID
    Order getOrderById(Long id);

    // Obtener todas las órdenes de un cliente
    List<Order> getOrdersByCustomer(Long customerId);

    // Obtener todas las órdenes con paginación
    Page<Order> getAllOrders(Pageable pageable);

    // Actualizar el estado de una orden
    Order updateOrderStatus(Long orderId, OrderStatus status);

    // Calcular el impacto ambiental de una orden
    Order calculateImpact(Long orderId);

    // Confirmar recepción de la orden (por el cliente)
    Order confirmDelivery(Long orderId);
}
