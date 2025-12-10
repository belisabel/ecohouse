package com.EcoHouse.order.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Monto del pago
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    // Fecha del pago
    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @PrePersist
    public void prePersist() {
        if (paymentDate == null) {
            paymentDate = LocalDateTime.now();
        }
    }
}
