package com.EcoHouse.shoppingCart.controller;

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

    /**
     * Obtener el carrito de un cliente
     */
    @Operation(summary = "Obtener carrito", description = "Obtiene el carrito de compras completo de un cliente")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ShoppingCart> getCart(@PathVariable Long customerId) {
        ShoppingCart cart = cartService.getCartByCustomer(customerId);
        return ResponseEntity.ok(cart);
    }

    /**
     * Agregar un producto al carrito
     */
    @Operation(summary = "Agregar item al carrito", description = "Agrega un producto al carrito con la cantidad especificada")
    @PostMapping("/customer/{customerId}/items")
    public ResponseEntity<ShoppingCart> addItem(
            @PathVariable Long customerId,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity) {
        ShoppingCart cart = cartService.addItem(customerId, productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    /**
     * Eliminar un producto del carrito
     */
    @Operation(summary = "Eliminar item del carrito", description = "Elimina completamente un producto del carrito")
    @DeleteMapping("/customer/{customerId}/items/{productId}")
    public ResponseEntity<ShoppingCart> removeItem(
            @PathVariable Long customerId,
            @PathVariable Long productId) {
        ShoppingCart cart = cartService.removeItem(customerId, productId);
        return ResponseEntity.ok(cart);
    }

    /**
     * Actualizar la cantidad de un producto en el carrito
     */
    @Operation(summary = "Actualizar cantidad", description = "Actualiza la cantidad de un producto en el carrito")
    @PutMapping("/customer/{customerId}/items/{productId}")
    public ResponseEntity<ShoppingCart> updateQuantity(
            @PathVariable Long customerId,
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        ShoppingCart cart = cartService.updateQuantity(customerId, productId, quantity);
        return ResponseEntity.ok(cart);
    }

    /**
     * Disminuir la cantidad de un producto en el carrito (en 1 unidad)
     */
    @Operation(summary = "Disminuir cantidad", description = "Disminuye en 1 la cantidad de un producto en el carrito")
    @PatchMapping("/customer/{customerId}/items/{productId}/decrease")
    public ResponseEntity<ShoppingCart> decreaseItem(
            @PathVariable Long customerId,
            @PathVariable Long productId) {
        ShoppingCart cart = cartService.decreaseItem(customerId, productId);
        return ResponseEntity.ok(cart);
    }

    /**
     * Aumentar la cantidad de un producto en el carrito (en 1 unidad)
     */
    @Operation(summary = "Aumentar cantidad", description = "Aumenta en 1 la cantidad de un producto en el carrito")
    @PatchMapping("/customer/{customerId}/items/{productId}/increase")
    public ResponseEntity<ShoppingCart> increaseItem(
            @PathVariable Long customerId,
            @PathVariable Long productId) {
        ShoppingCart cart = cartService.addItem(customerId, productId, 1);
        return ResponseEntity.ok(cart);
    }

    /**
     * Limpiar todo el carrito
     */
    @Operation(summary = "Limpiar carrito", description = "Elimina todos los productos del carrito")
    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<ShoppingCart> clearCart(@PathVariable Long customerId) {
        ShoppingCart cart = cartService.clearCart(customerId);
        return ResponseEntity.ok(cart);
    }

    /**
     * Obtener el total del carrito
     */
    @Operation(summary = "Obtener total del carrito", description = "Devuelve el monto total del carrito")
    @GetMapping("/customer/{customerId}/total")
    public ResponseEntity<Map<String, Object>> getCartTotal(@PathVariable Long customerId) {
        BigDecimal total = cartService.getCartTotal(customerId);
        Map<String, Object> response = new HashMap<>();
        response.put("customerId", customerId);
        response.put("total", total);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener el número de items en el carrito
     */
    @Operation(summary = "Contar items", description = "Devuelve la cantidad total de items en el carrito")
    @GetMapping("/customer/{customerId}/count")
    public ResponseEntity<Map<String, Object>> getItemCount(@PathVariable Long customerId) {
        Integer count = cartService.getItemCount(customerId);
        Map<String, Object> response = new HashMap<>();
        response.put("customerId", customerId);
        response.put("itemCount", count);
        return ResponseEntity.ok(response);
    }

    /**
     * Verificar si un producto existe en el carrito
     */
    @Operation(summary = "Verificar item", description = "Verifica si un producto específico está en el carrito")
    @GetMapping("/customer/{customerId}/items/{productId}/exists")
    public ResponseEntity<Map<String, Object>> checkItemExists(
            @PathVariable Long customerId,
            @PathVariable Long productId) {
        boolean exists = cartService.existsItem(customerId, productId);
        Map<String, Object> response = new HashMap<>();
        response.put("customerId", customerId);
        response.put("productId", productId);
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener resumen del carrito (total + cantidad)
     */
    @Operation(summary = "Resumen del carrito", description = "Devuelve un resumen con el total y cantidad de items")
    @GetMapping("/customer/{customerId}/summary")
    public ResponseEntity<Map<String, Object>> getCartSummary(@PathVariable Long customerId) {
        BigDecimal total = cartService.getCartTotal(customerId);
        Integer count = cartService.getItemCount(customerId);

        Map<String, Object> response = new HashMap<>();
        response.put("customerId", customerId);
        response.put("total", total);
        response.put("itemCount", count);
        response.put("isEmpty", count == 0);

        return ResponseEntity.ok(response);
    }
}

