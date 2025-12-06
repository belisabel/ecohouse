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
        // ✅ Solo incluimos datos básicos del producto, sin acceder a relaciones lazy
        // Esto evita LazyInitializationException en producción
        ProductResponse productResponse = ProductResponse.builder()
                .id(item.getProduct().getId())
                .name(item.getProduct().getName())
                .description(item.getProduct().getDescription())
                .price(item.getProduct().getPrice())
                .imageUrl(item.getProduct().getImageUrl())
                .additionalImages(item.getProduct().getAdditionalImages())
                .stock(item.getProduct().getStock())
                // ❌ NO accedemos a Brand (lazy)
                .brandId(null)
                .brandName(null)
                // ❌ NO accedemos a Category (lazy)
                .categoryId(null)
                .categoryName(null)
                // ❌ NO accedemos a EnvironmentalData (lazy)
                .environmentalData(null)
                // ❌ NO accedemos a Certifications (lazy)
                .certificationIds(null)
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
