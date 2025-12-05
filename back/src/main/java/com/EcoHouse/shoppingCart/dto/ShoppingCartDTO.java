package com.EcoHouse.shoppingCart.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartDTO {

    private Long id;
    private List<CartItemDTO> items;
    private BigDecimal totalPrice;
    private BigDecimal estimatedCarbonFootprint;


}
