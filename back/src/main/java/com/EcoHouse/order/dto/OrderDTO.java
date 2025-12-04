package com.EcoHouse.order.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.EcoHouse.order.model.OrderStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Long id;

    private Long customerId;

    private String orderNumber;

    private List<OrderItemDTO> items;

    private BigDecimal totalAmount;

    private BigDecimal totalCarbonFootprint;

    private BigDecimal co2Saved;

    private OrderStatus status;

    private ShippingAddressDTO shippingAddress;

    private PaymentDTO payment;

    private Date orderDate;

    private Date shippingDate;

    private Date deliveryDate;

    private Integer ecoPointsEarned;
}
