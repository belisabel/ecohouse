package com.EcoHouse.order.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tipo de pago (tarjeta, transferencia, MP, etc.)
    @Column(nullable = false, length = 30)
    private String paymentMethod;

    // Estado del pago (PAID, FAILED, PENDING...)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status;

    // Monto abonado
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    // Identificador externo (Stripe, MP, etc.)
    private String transactionId;

    // Fecha del pago
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;
}
