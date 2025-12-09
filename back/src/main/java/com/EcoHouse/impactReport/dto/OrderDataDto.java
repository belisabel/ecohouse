package com.EcoHouse.impactReport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para transferir datos de órdenes al servicio de reportes de impacto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDataDto {
    private Long orderId;
    private String orderNumber;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private BigDecimal totalCarbonFootprint;
    private BigDecimal co2Saved;
    private Integer ecoPointsEarned;
    private String status;

    // Datos de items
    private List<OrderItemDataDto> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemDataDto {
        private Long productId;
        private String productName;
        private String categoryName;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal subtotal;
        private BigDecimal itemCarbonFootprint;
        private BigDecimal co2Saved;
        private Boolean isSustainable; // Si tiene certificaciones o bajo impacto
    }
}

