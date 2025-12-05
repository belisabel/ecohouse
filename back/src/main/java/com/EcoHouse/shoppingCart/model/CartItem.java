package com.EcoHouse.shoppingCart.model;

import com.EcoHouse.product.model.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Producto agregado al carrito
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Cantidad
    @Column(nullable = false)
    private Integer quantity;

    // Precio unitario del momento
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    // Subtotal = unitPrice * quantity
    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;

    // Huella ecológica del ítem (kg CO2)
    @Column(precision = 12, scale = 4)
    private BigDecimal itemCarbonFootprint;

    // CO₂ ahorrado (si aplica)
    @Column(precision = 12, scale = 4)
    private BigDecimal co2Saved;

    // Carrito al que pertenece
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private ShoppingCart shoppingCart;

    // ------------------------------------------------------
    // UTILIDAD: Calcular subtotal
    // ------------------------------------------------------
    public void calculateSubtotal() {
        if (unitPrice != null && quantity != null) {
            this.subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
    }
}
