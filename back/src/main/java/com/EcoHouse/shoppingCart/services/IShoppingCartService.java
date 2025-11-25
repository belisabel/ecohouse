package com.EcoHouse.shoppingCart.services;

import java.math.BigDecimal;

import com.EcoHouse.shoppingCart.model.ShoppingCart;

public interface IShoppingCartService {

    // Obtiene el carrito de compras de un cliente por su ID
    ShoppingCart getCartByCustomer(Long customerId);

    // Agrega un ítem al carrito de compras
    ShoppingCart addItem(Long customerId, Long productId, Integer quantity);

    // Elimina un ítem del carrito de compras
    ShoppingCart removeItem(Long customerId, Long productId);

    // Actualiza la cantidad de un ítem en el carrito de compras
    ShoppingCart updateQuantity(Long customerId, Long productId, Integer quantity);

    // Limpia todos los ítems del carrito de compras
    ShoppingCart clearCart(Long customerId);

    // Obtiene el total del carrito de compras
    BigDecimal getCartTotal(Long customerId);

    // Obtiene el conteo de ítems en el carrito de compras
    Integer getItemCount(Long customerId);

    // Disminuye la cantidad de un ítem en el carrito de compras
    ShoppingCart decreaseItem(Long customerId, Long productId);

    // Verifica si un ítem existe en el carrito de compras
    boolean existsItem(Long customerId, Long productId);
}
