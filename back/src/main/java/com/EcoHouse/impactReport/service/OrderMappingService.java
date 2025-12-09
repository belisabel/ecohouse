package com.EcoHouse.impactReport.service;

import com.EcoHouse.impactReport.dto.OrderDataDto;
import com.EcoHouse.order.model.Order;
import com.EcoHouse.order.model.OrderItem;
import com.EcoHouse.product.model.EnvironmentalData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para mapear entidades Order a DTOs para reportes
 * Asegura que todos los datos de productos, categorías y certificaciones estén disponibles
 */
@Service
@Slf4j
public class OrderMappingService {

    /**
     * Convierte una lista de órdenes a DTOs para procesamiento en reportes
     */
    public List<OrderDataDto> mapOrdersToDto(List<Order> orders) {
        return orders.stream()
                .map(this::mapOrderToDto)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una orden individual a DTO
     */
    public OrderDataDto mapOrderToDto(Order order) {
        return OrderDataDto.builder()
                .orderId(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderDate(order.getOrderDate() != null
                    ? order.getOrderDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
                    : null)
                .totalAmount(order.getTotalAmount() != null ? order.getTotalAmount() : BigDecimal.ZERO)
                .totalCarbonFootprint(order.getTotalCarbonFootprint() != null
                    ? order.getTotalCarbonFootprint()
                    : BigDecimal.ZERO)
                .co2Saved(order.getCo2Saved() != null ? order.getCo2Saved() : BigDecimal.ZERO)
                .ecoPointsEarned(order.getEcoPointsEarned() != null ? order.getEcoPointsEarned() : 0)
                .status(order.getStatus() != null ? order.getStatus().name() : "UNKNOWN")
                .items(mapOrderItems(order.getItems()))
                .build();
    }

    /**
     * Mapea los items de una orden
     */
    private List<OrderDataDto.OrderItemDataDto> mapOrderItems(List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            return List.of();
        }

        return items.stream()
                .map(this::mapOrderItem)
                .collect(Collectors.toList());
    }

    /**
     * Mapea un item individual
     * Determina si un producto es sustentable basándose en:
     * 1. Certificaciones ecológicas
     * 2. Huella de carbono baja (<= 5 kg CO2)
     * 3. Porcentaje de reciclabilidad alto (>= 70%)
     * 4. CO2 ahorrado positivo
     */
    private OrderDataDto.OrderItemDataDto mapOrderItem(OrderItem item) {
        boolean isSustainable = false;
        String categoryName = "Sin categoría";
        String productName = "Producto desconocido";
        Long productId = null;

        if (item.getProduct() != null) {
            productId = item.getProduct().getId();
            productName = item.getProduct().getName();

            // Criterio 1: Tiene certificaciones ecológicas
            if (item.getProduct().getCertifications() != null &&
                !item.getProduct().getCertifications().isEmpty()) {
                isSustainable = true;
                log.debug("Product {} marked as sustainable due to certifications", productId);
            }

            // Criterio 2: Huella de carbono baja por unidad
            if (!isSustainable && item.getItemCarbonFootprint() != null &&
                item.getItemCarbonFootprint().compareTo(BigDecimal.valueOf(5)) <= 0) {
                isSustainable = true;
                log.debug("Product {} marked as sustainable due to low carbon footprint", productId);
            }

            // Criterio 3: Alto porcentaje de reciclabilidad
            if (!isSustainable && item.getProduct().getEnvironmentalData() != null) {
                EnvironmentalData envData = item.getProduct().getEnvironmentalData();
                if (envData.getRecyclablePercentage() != null &&
                    envData.getRecyclablePercentage().compareTo(BigDecimal.valueOf(70)) >= 0) {
                    isSustainable = true;
                    log.debug("Product {} marked as sustainable due to high recyclability", productId);
                }
            }

            // Criterio 4: CO2 ahorrado positivo
            if (!isSustainable && item.getCo2Saved() != null &&
                item.getCo2Saved().compareTo(BigDecimal.ZERO) > 0) {
                isSustainable = true;
                log.debug("Product {} marked as sustainable due to CO2 saved", productId);
            }

            // Obtener categoría
            if (item.getProduct().getCategory() != null) {
                categoryName = item.getProduct().getCategory().getName();
            }
        }

        return OrderDataDto.OrderItemDataDto.builder()
                .productId(productId)
                .productName(productName)
                .categoryName(categoryName)
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice() != null ? item.getUnitPrice() : BigDecimal.ZERO)
                .subtotal(item.getSubtotal() != null ? item.getSubtotal() : BigDecimal.ZERO)
                .itemCarbonFootprint(item.getItemCarbonFootprint() != null
                    ? item.getItemCarbonFootprint()
                    : BigDecimal.ZERO)
                .co2Saved(item.getCo2Saved() != null ? item.getCo2Saved() : BigDecimal.ZERO)
                .isSustainable(isSustainable)
                .build();
    }
}

