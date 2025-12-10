package com.EcoHouse.shoppingCart.model;

import com.EcoHouse.user.model.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Relación con el Customer
    @JsonIgnore // Evita LazyInitializationException
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // 🔗 Relación con los items del carrito
    @JsonIgnore // Evita LazyInitializationException - usamos el mapper para serializarlos
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderColumn(name = "item_order")
    private List<CartItem> items = new ArrayList<>();

    // 💰 Total exacto
    private BigDecimal totalPrice = BigDecimal.ZERO;

    // ♻️ Impacto ecológico (puede ser decimal también)
    private BigDecimal estimatedCarbonFootprint = BigDecimal.ZERO;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // 👉 Constructor usado cuando no existe carrito todavía
    public ShoppingCart(Long customerId) {
        this.customer = new Customer();
        this.customer.setId(customerId);
        this.items = new ArrayList<>();
    }

    // 📌 Se calculan los totales del carrito
    public void calculateTotal() {
        this.totalPrice = items.stream()
                .filter(item -> item.getSubtotal() != null)
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void calculateEcoImpact() {
        // Ejemplo: 0.1 kg CO2 por producto (ajustar después)
        this.estimatedCarbonFootprint = items.stream()
                .map(item -> BigDecimal.valueOf(item.getQuantity()).multiply(BigDecimal.valueOf(0.1)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
