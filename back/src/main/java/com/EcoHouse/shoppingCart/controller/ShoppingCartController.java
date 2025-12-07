package com.EcoHouse.shoppingCart.controller;

import com.EcoHouse.shoppingCart.dto.ShoppingCartDTO;
import com.EcoHouse.shoppingCart.mapper.ShoppingCartMapper;
import com.EcoHouse.shoppingCart.model.ShoppingCart;
import com.EcoHouse.shoppingCart.services.IShoppingCartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:3000"
})
@Tag(name = "Shopping Cart", description = "API para gestión del carrito de compras")
public class ShoppingCartController {

    private final IShoppingCartService cartService;
    private final ShoppingCartMapper shoppingCartMapper;

    /**
     * Obtener el carrito de un cliente
     */
    @Operation(summary = "Obtener carrito", description = "Obtiene el carrito de compras completo de un cliente")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ShoppingCartDTO> getCart(@PathVariable Long customerId) {
        ShoppingCart cart = cartService.getCartByCustomer(customerId);
        ShoppingCartDTO dto = shoppingCartMapper.toShoppingCartDTO(cart);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Agregar item al carrito", description = "Agrega un producto al carrito con la cantidad especificada")
    @PostMapping("/customer/{customerId}/items")
    public ResponseEntity<ShoppingCartDTO> addItem(
            @PathVariable Long customerId,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity) {

        ShoppingCart cart = cartService.addItem(customerId, productId, quantity);
        ShoppingCartDTO dto = shoppingCartMapper.toShoppingCartDTO(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Operation(summary = "Eliminar item del carrito", description = "Elimina completamente un producto del carrito")
    @DeleteMapping("/customer/{customerId}/items/{productId}")
    public ResponseEntity<ShoppingCartDTO> removeItem(
            @PathVariable Long customerId,
            @PathVariable Long productId) {

        ShoppingCart cart = cartService.removeItem(customerId, productId);
        ShoppingCartDTO dto = shoppingCartMapper.toShoppingCartDTO(cart);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Actualizar cantidad", description = "Actualiza la cantidad de un producto en el carrito")
    @PutMapping("/customer/{customerId}/items/{productId}")
    public ResponseEntity<ShoppingCartDTO> updateQuantity(
            @PathVariable Long customerId,
            @PathVariable Long productId,
            @RequestParam Integer quantity) {

        ShoppingCart cart = cartService.updateQuantity(customerId, productId, quantity);
        ShoppingCartDTO dto = shoppingCartMapper.toShoppingCartDTO(cart);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Disminuir cantidad", description = "Disminuye en 1 la cantidad de un producto en el carrito")
    @PatchMapping("/customer/{customerId}/items/{productId}/decrease")
    public ResponseEntity<ShoppingCartDTO> decreaseItem(
            @PathVariable Long customerId,
            @PathVariable Long productId) {

        ShoppingCart cart = cartService.decreaseItem(customerId, productId);
        ShoppingCartDTO dto = shoppingCartMapper.toShoppingCartDTO(cart);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Aumentar cantidad", description = "Aumenta en 1 la cantidad de un producto en el carrito")
    @PatchMapping("/customer/{customerId}/items/{productId}/increase")
    public ResponseEntity<ShoppingCartDTO> increaseItem(
            @PathVariable Long customerId,
            @PathVariable Long productId) {

        ShoppingCart cart = cartService.addItem(customerId, productId, 1);
        ShoppingCartDTO dto = shoppingCartMapper.toShoppingCartDTO(cart);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Limpiar carrito", description = "Elimina todos los productos del carrito")
    @DeleteMapping("/customer/{customerId}/clear")
    public ResponseEntity<ShoppingCartDTO> clearCart(@PathVariable Long customerId) {
        ShoppingCart cart = cartService.clearCart(customerId);
        ShoppingCartDTO dto = shoppingCartMapper.toShoppingCartDTO(cart);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Obtener total del carrito", description = "Calcula y devuelve el precio total del carrito")
    @GetMapping("/customer/{customerId}/total")
    public ResponseEntity<Map<String, BigDecimal>> getCartTotal(@PathVariable Long customerId) {
        BigDecimal total = cartService.getCartTotal(customerId);
        Map<String, BigDecimal> response = new HashMap<>();
        response.put("totalPrice", total);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener resumen del carrito", description = "Devuelve el número de items y el precio total")
    @GetMapping("/customer/{customerId}/summary")
    public ResponseEntity<Map<String, Object>> getCartSummary(@PathVariable Long customerId) {
        Integer itemCount = cartService.getItemCount(customerId);
        BigDecimal totalPrice = cartService.getCartTotal(customerId);
        Map<String, Object> response = new HashMap<>();
        response.put("itemCount", itemCount);
        response.put("totalPrice", totalPrice);
        return ResponseEntity.ok(response);
    }
}
