package com.EcoHouse.shoppingCart.mapper;

import com.EcoHouse.product.dto.ProductResponse;
import com.EcoHouse.product.model.Certification;
import com.EcoHouse.product.mapper.ProductMapper;
import com.EcoHouse.shoppingCart.dto.CartItemDTO;
import com.EcoHouse.shoppingCart.dto.ShoppingCartDTO;
import com.EcoHouse.shoppingCart.model.CartItem;
import com.EcoHouse.shoppingCart.model.ShoppingCart;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ShoppingCartMapper {

    private final ProductMapper productMapper;

    public ShoppingCartMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public CartItemDTO toCartItemDTO(CartItem item) {
        ProductResponse productResponse = productMapper.toDTO(item.getProduct());

        return CartItemDTO.builder()
                .id(item.getId())
                .product(productResponse)
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .subtotal(item.getSubtotal())
                .build();
    }

    public ShoppingCartDTO toShoppingCartDTO(ShoppingCart cart) {
        return new ShoppingCartDTO(
                cart.getId(),
                cart.getItems() != null ?
                        cart.getItems().stream()
                                .map(this::toCartItemDTO)
                                .collect(Collectors.toList())
                        : null,
                cart.getTotalPrice(),
                cart.getEstimatedCarbonFootprint()
        );
    }


}
