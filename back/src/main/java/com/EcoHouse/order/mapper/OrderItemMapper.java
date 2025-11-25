package com.EcoHouse.order.mapper;

import com.EcoHouse.order.dto.OrderItemDTO;
import com.EcoHouse.order.model.OrderItem;
import com.EcoHouse.product.model.Product;

public class OrderItemMapper {

    // ---- ENTITY -> DTO ----
    public static OrderItemDTO toDTO(OrderItem item) {
        if (item == null) {
            return null;
        }

        return OrderItemDTO.builder()
                .id(item.getId())
                .productId(item.getProduct() != null ? item.getProduct().getId() : null)
                .productName(item.getProduct() != null ? item.getProduct().getName() : null)
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .totalPrice(item.getSubtotal())
                .carbonFootprint(item.getItemCarbonFootprint())
                .build();
    }

    // ---- DTO -> ENTITY ----
    // ⚠ El Product se pasa desde el servicio, porque el DTO no tiene el Product completo
    public static OrderItem toEntity(OrderItemDTO dto, Product product) {
        if (dto == null) {
            return null;
        }

        OrderItem item = new OrderItem();
        item.setId(dto.getId());
        item.setProduct(product);
        item.setQuantity(dto.getQuantity());
        item.setUnitPrice(dto.getUnitPrice());
        item.setSubtotal(dto.getTotalPrice());
        item.setItemCarbonFootprint(dto.getCarbonFootprint());

        return item;
    }
}
