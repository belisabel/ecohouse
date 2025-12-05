package com.EcoHouse.order.dto;

import lombok.*;
import java.math.BigDecimal;

/**
 * DTO de respuesta para OrderItem - incluye id autogenerado
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private BigDecimal carbonFootprint;
}

