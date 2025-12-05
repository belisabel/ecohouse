package com.EcoHouse.order.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.EcoHouse.order.model.OrderStatus;

import lombok.*;

/**
 * DTO de respuesta para Order - incluye id y timestamps autogenerados
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private Long customerId;
    private String orderNumber;
    private List<OrderItemResponse> items;
    private BigDecimal totalAmount;
    private BigDecimal totalCarbonFootprint;
    private BigDecimal co2Saved;
    private OrderStatus status;
    private ShippingAddressResponse shippingAddress;
    private PaymentResponse payment;
    private Date orderDate;
    private Date shippingDate;
    private Date deliveryDate;

    private Integer ecoPointsEarned;
}
