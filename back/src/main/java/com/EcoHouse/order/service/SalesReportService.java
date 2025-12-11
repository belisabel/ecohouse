package com.EcoHouse.order.service;

import com.EcoHouse.order.model.Order;
import com.EcoHouse.order.model.OrderStatus;
import com.EcoHouse.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalesReportService {

    private final OrderRepository orderRepository;

    /**
     * Obtener el total de ventas de todas las órdenes completadas
     */
    @Transactional(readOnly = true)
    public BigDecimal getTotalSales() {
        List<Order> completedOrders = orderRepository.findAll().stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .toList();

        return completedOrders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Obtener el total de ventas por rango de fechas
     */
    @Transactional(readOnly = true)
    public BigDecimal getTotalSalesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        Date start = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());

        List<Order> orders = orderRepository.findAll().stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .filter(order -> order.getOrderDate() != null)
                .filter(order -> !order.getOrderDate().before(start) && !order.getOrderDate().after(end))
                .toList();

        return orders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Obtener reporte de ventas por cliente
     */
    @Transactional(readOnly = true)
    public Map<String, BigDecimal> getSalesByCustomer() {
        List<Order> completedOrders = orderRepository.findAll().stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .toList();

        Map<String, BigDecimal> salesByCustomer = new HashMap<>();

        for (Order order : completedOrders) {
            String customerName = order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName();
            BigDecimal currentTotal = salesByCustomer.getOrDefault(customerName, BigDecimal.ZERO);
            salesByCustomer.put(customerName, currentTotal.add(order.getTotalAmount()));
        }

        return salesByCustomer;
    }

    /**
     * Obtener estadísticas completas de ventas
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getSalesStatistics() {
        List<Order> completedOrders = orderRepository.findAll().stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .toList();

        BigDecimal totalSales = completedOrders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageOrderValue = completedOrders.isEmpty()
                ? BigDecimal.ZERO
                : totalSales.divide(BigDecimal.valueOf(completedOrders.size()), 2, BigDecimal.ROUND_HALF_UP);

        long totalOrders = completedOrders.size();

        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalSales", totalSales);
        statistics.put("totalOrders", totalOrders);
        statistics.put("averageOrderValue", averageOrderValue);
        statistics.put("currency", "USD");

        return statistics;
    }

    /**
     * Obtener el valor total de pagos realizados
     */
    @Transactional(readOnly = true)
    public BigDecimal getTotalPayments() {
        List<Order> completedOrders = orderRepository.findAll().stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .filter(order -> order.getPayment() != null)
                .toList();

        return completedOrders.stream()
                .map(order -> order.getPayment().getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Obtener ventas totales de un cliente específico
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getSalesByCustomerId(Long customerId) {
        List<Order> customerOrders = orderRepository.findAll().stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .filter(order -> order.getCustomer() != null)
                .filter(order -> order.getCustomer().getId().equals(customerId))
                .toList();

        if (customerOrders.isEmpty()) {
            return Map.of();
        }

        BigDecimal totalSales = customerOrders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long totalOrders = customerOrders.size();

        BigDecimal averageOrderValue = totalSales.divide(
                BigDecimal.valueOf(totalOrders),
                2,
                BigDecimal.ROUND_HALF_UP
        );

        // Obtener nombre del cliente desde la primera orden
        String customerName = customerOrders.get(0).getCustomer().getFirstName() + " " +
                             customerOrders.get(0).getCustomer().getLastName();

        Map<String, Object> result = new HashMap<>();
        result.put("customerId", customerId);
        result.put("customerName", customerName);
        result.put("totalSales", totalSales);
        result.put("totalOrders", totalOrders);
        result.put("averageOrderValue", averageOrderValue);
        result.put("currency", "USD");

        return result;
    }
}

