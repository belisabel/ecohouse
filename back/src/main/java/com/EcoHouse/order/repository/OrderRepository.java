package com.EcoHouse.order.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.EcoHouse.order.model.Order;
import com.EcoHouse.order.model.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Buscar todas las órdenes por el ID del cliente
    List<Order> findByCustomerId(Long customerId);

    // Buscar una orden por su número de orden
    Optional<Order> findByOrderNumber(String orderNumber);

    // Buscar órdenes por cliente y rango de fechas
    List<Order> findByCustomerIdAndOrderDateBetween(Long customerId, Date startDate, Date endDate);

    // Buscar órdenes por cliente, estado y rango de fechas
    List<Order> findByCustomerIdAndStatusAndOrderDateBetween(
        Long customerId,
        OrderStatus status,
        Date startDate,
        Date endDate
    );

    // Buscar órdenes completadas/entregadas por cliente en un rango de fechas
    // Carga eager de todas las relaciones necesarias para reportes de impacto
    @Query("SELECT DISTINCT o FROM Order o " +
           "LEFT JOIN FETCH o.items i " +
           "LEFT JOIN FETCH i.product p " +
           "LEFT JOIN FETCH p.category " +
           "LEFT JOIN FETCH p.environmentalData " +
           "LEFT JOIN FETCH p.certifications " +
           "WHERE o.customer.id = :customerId " +
           "AND o.status IN ('DELIVERED', 'RECEIVED') " +
           "AND o.orderDate BETWEEN :startDate AND :endDate " +
           "ORDER BY o.orderDate DESC")
    List<Order> findCompletedOrdersByCustomerAndDateRange(
        @Param("customerId") Long customerId,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate
    );
}
