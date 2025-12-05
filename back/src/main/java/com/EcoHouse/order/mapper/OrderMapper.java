package com.EcoHouse.order.mapper;

import com.EcoHouse.order.dto.OrderResponse;
import com.EcoHouse.order.dto.OrderRequest;
import com.EcoHouse.order.model.Order;

import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderResponse toDTO(Order order) {
        if (order == null) {
            return null;
        }

        return OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomer() != null ? order.getCustomer().getId() : null)
                .orderNumber(order.getOrderNumber())
                .items(order.getItems() != null
                        ? order.getItems().stream().map(OrderItemMapper::toDTO).collect(Collectors.toList())
                        : null)
                .totalAmount(order.getTotalAmount())
                .totalCarbonFootprint(order.getTotalCarbonFootprint())
                .co2Saved(order.getCo2Saved())
                .status(order.getStatus())
                .shippingAddress(ShippingAddressMapper.toDTO(order.getShippingAddress()))
                .payment(order.getPayment() != null ? PaymentMapper.toDto(order.getPayment()) : null)
                .orderDate(order.getOrderDate())
                .shippingDate(order.getShippingDate())
                .deliveryDate(order.getDeliveryDate())
                .ecoPointsEarned(order.getEcoPointsEarned())
                .build();
    }

    public static Order toEntity(OrderRequest request) {
        if (request == null) {
            return null;
        }

        Order order = new Order();
        order.setShippingAddress(ShippingAddressMapper.toEntity(request.getShippingAddress()));
        order.setPayment(PaymentMapper.toEntity(request.getPayment()));
        // Los demás campos se setean en el service

        return order;
    }
}
