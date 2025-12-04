package com.EcoHouse.order.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.*;

/**
 * DTO de request para Order - sin id ni timestamps (para POST)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private Long customerId;
    private List<OrderItemRequest> items;
    private ShippingAddressRequest shippingAddress;
    private PaymentRequest payment;
}

