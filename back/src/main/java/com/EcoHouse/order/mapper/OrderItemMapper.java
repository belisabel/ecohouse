package com.EcoHouse.order.mapper;

import com.EcoHouse.order.dto.OrderItemResponse;
import com.EcoHouse.order.dto.OrderItemRequest;
import com.EcoHouse.order.model.OrderItem;
import com.EcoHouse.product.model.Product;

public class OrderItemMapper {

    public static OrderItemResponse toDTO(OrderItem item) {
        if (item == null) {
            return null;
        }

        return OrderItemResponse.builder()
                .id(item.getId())
                .productId(item.getProduct() != null ? item.getProduct().getId() : null)
                .productName(item.getProduct() != null ? item.getProduct().getName() : null)
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .totalPrice(item.getSubtotal())
                .carbonFootprint(item.getItemCarbonFootprint())
                .build();
    }

    public static OrderItem toEntity(OrderItemRequest request, Product product) {
        if (request == null) {
            return null;
        }

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(request.getQuantity());
        // unitPrice, totalPrice y carbonFootprint se calculan en el service
        return item;
    }
}
