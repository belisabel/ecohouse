package com.EcoHouse.order.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.EcoHouse.customer.model.Customer;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // Número único de pedido
    @Column(nullable = false, unique = true, length = 50)
    private String orderNumber;

    // Items del pedido
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    // Totales
    @Column(precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(precision = 12, scale = 4)
    private BigDecimal totalCarbonFootprint;

    @Column(precision = 12, scale = 4)
    private BigDecimal co2Saved;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;

    // Dirección de envío
    @Embedded
    private ShippingAddress shippingAddress;

    // Pago
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    // Fechas
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date shippingDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;

    // Puntos verdes
    private Integer ecoPointsEarned;

    // ---------------------------------------------------------------------
    // CÁLCULO DE IMPACTO Y MONTO
    // ---------------------------------------------------------------------
    public void calculateImpact() {

        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal carbon = BigDecimal.ZERO;
        BigDecimal saved = BigDecimal.ZERO;

        if (items != null) {
            for (OrderItem item : items) {

                // Asegurar que el subtotal esté calculado
                item.calculateSubtotal();

                // Total de dinero
                if (item.getSubtotal() != null) {
                    amount = amount.add(item.getSubtotal());
                }

                // Huella de carbono total
                if (item.getItemCarbonFootprint() != null) {
                    BigDecimal footprintItem = item.getItemCarbonFootprint()
                            .multiply(BigDecimal.valueOf(item.getQuantity()));
                    carbon = carbon.add(footprintItem);
                }

                // CO₂ ahorrado
                if (item.getCo2Saved() != null) {
                    BigDecimal savedItem = item.getCo2Saved()
                            .multiply(BigDecimal.valueOf(item.getQuantity()));
                    saved = saved.add(savedItem);
                }
            }
        }

        this.totalAmount = amount;
        this.totalCarbonFootprint = carbon;
        this.co2Saved = saved;
    }

    // ---------------------------------------------------------------------
    // Lifecycle hooks
    // ---------------------------------------------------------------------
    @PrePersist
    public void prePersist() {
        if (orderDate == null) orderDate = new Date();
        if (status == null) status = OrderStatus.PENDING;
        calculateImpact();
    }

    @PreUpdate
    public void preUpdate() {
        calculateImpact();
    }
}
