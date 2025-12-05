package com.EcoHouse.order.dto;

import lombok.*;
import java.math.BigDecimal;

/**
 * DTO de request para OrderItem - sin id (para POST)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequest {
    private Long productId;
    private Integer quantity;
}

