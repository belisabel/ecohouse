package com.EcoHouse.order.model;

import java.math.BigDecimal;

import com.EcoHouse.product.model.Product;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "item_carbon_footprint", precision = 15, scale = 4)
    private BigDecimal itemCarbonFootprint;

    @Column(name = "co2_saved", precision = 15, scale = 4)
    private BigDecimal cO2Saved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    // --------------------------------------------------------------------
    // CALCULOS
    // --------------------------------------------------------------------

    /**
     * Calcula el subtotal basado en la cantidad y el precio unitario.
     */
    public void calculateSubtotal() {
        if (unitPrice != null && quantity != null) {
            this.subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
        } else {
            this.subtotal = BigDecimal.ZERO;
        }
    }

    /**
     * Retorna el CO₂ ahorrado por el ítem.
     * Si no existe, devuelve 0.
     */
    public BigDecimal getCo2Saved() {
        return cO2Saved != null ? cO2Saved : BigDecimal.ZERO;
    }

    // --------------------------------------------------------------------
    // LIFECYCLE HOOKS
    // --------------------------------------------------------------------
    @PrePersist
    @PreUpdate
    public void preSave() {
        calculateSubtotal();
        if (itemCarbonFootprint == null) {
            itemCarbonFootprint = BigDecimal.ZERO;
        }
    }
}
