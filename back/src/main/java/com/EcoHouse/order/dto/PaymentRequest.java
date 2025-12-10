package com.EcoHouse.order.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de request para Payment - estructura simplificada
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private BigDecimal amount;
    private LocalDateTime paymentDate; // Opcional, si no se proporciona se usa la fecha actual
}

