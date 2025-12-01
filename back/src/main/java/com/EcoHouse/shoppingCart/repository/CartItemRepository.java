package com.EcoHouse.shoppingCart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EcoHouse.shoppingCart.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Obtener todos los ítems de un carrito
    List<CartItem> findByShoppingCartId(Long cartId);

    // Eliminar todos los ítems de un carrito
    void deleteByShoppingCartId(Long cartId);

    // Buscar un ítem específico por carrito + producto (para evitar duplicados)
    Optional<CartItem> findByShoppingCartIdAndProductId(Long cartId, Long productId);

    // Verificar si un producto ya está en el carrito
    boolean existsByShoppingCartIdAndProductId(Long cartId, Long productId);
}
