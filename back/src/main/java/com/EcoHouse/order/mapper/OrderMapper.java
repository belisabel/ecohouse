package com.EcoHouse.order.mapper;

import com.EcoHouse.order.dto.OrderDTO;
import com.EcoHouse.order.model.Order;

import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }

        return OrderDTO.builder()
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

    public static Order toEntity(OrderDTO dto) {
        if (dto == null) {
            return null;
        }

        Order order = new Order();
        order.setId(dto.getId());
        order.setOrderNumber(dto.getOrderNumber());
        order.setTotalAmount(dto.getTotalAmount());
        order.setTotalCarbonFootprint(dto.getTotalCarbonFootprint());
        order.setCo2Saved(dto.getCo2Saved());
        order.setStatus(dto.getStatus());
        order.setShippingAddress(ShippingAddressMapper.toEntity(dto.getShippingAddress()));
        order.setPayment(PaymentMapper.toEntity(dto.getPayment()));
        order.setOrderDate(dto.getOrderDate());
        order.setShippingDate(dto.getShippingDate());
        order.setDeliveryDate(dto.getDeliveryDate());
        order.setEcoPointsEarned(dto.getEcoPointsEarned());

        return order;
    }
}
