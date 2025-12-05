package com.EcoHouse.shoppingCart.mapper;

import com.EcoHouse.product.dto.ProductResponse;
import com.EcoHouse.product.mapper.EnvironmentalDataMapper;
import com.EcoHouse.product.model.Certification;
import com.EcoHouse.shoppingCart.dto.CartItemDTO;
import com.EcoHouse.shoppingCart.dto.ShoppingCartDTO;
import com.EcoHouse.shoppingCart.model.CartItem;
import com.EcoHouse.shoppingCart.model.ShoppingCart;

import java.util.stream.Collectors;

public class ShoppingCartMapper {

    public static CartItemDTO toCartItemDTO(CartItem item) {
        ProductResponse productResponse = ProductResponse.builder()
                .id(item.getProduct().getId())
                .name(item.getProduct().getName())
                .description(item.getProduct().getDescription())
                .price(item.getProduct().getPrice())
                .imageUrl(item.getProduct().getImageUrl())
                .additionalImages(item.getProduct().getAdditionalImages())
                .stock(item.getProduct().getStock())
                .brandId(item.getProduct().getBrand() != null ? item.getProduct().getBrand().getId() : null)
                .brandName(item.getProduct().getBrand() != null ? item.getProduct().getBrand().getName() : null)
                .categoryId(item.getProduct().getCategory() != null ? item.getProduct().getCategory().getId() : null)
                .categoryName(item.getProduct().getCategory() != null ? item.getProduct().getCategory().getName() : null)
                .environmentalData(EnvironmentalDataMapper.toDTO(item.getProduct().getEnvironmentalData()))
                .certificationIds(item.getProduct().getCertifications() != null ?
                        item.getProduct().getCertifications().stream()
                                .map(Certification::getId)
                                .collect(Collectors.toList())
                        : null
                )
                .isActive(item.getProduct().getIsActive())
                .createdAt(item.getProduct().getCreatedAt())
                .build();

        return new CartItemDTO(
                item.getId(),
                productResponse,
                item.getQuantity(),
                item.getUnitPrice(),
                item.getSubtotal()
        );
    }

    public static ShoppingCartDTO toShoppingCartDTO(ShoppingCart cart) {
        return new ShoppingCartDTO(
                cart.getId(),
                cart.getItems() != null ?
                        cart.getItems().stream()
                                .map(ShoppingCartMapper::toCartItemDTO)
                                .collect(Collectors.toList())
                        : null,
                cart.getTotalPrice(),
                cart.getEstimatedCarbonFootprint()
        );
    }


}
