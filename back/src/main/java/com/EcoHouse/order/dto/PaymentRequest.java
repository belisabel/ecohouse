package com.EcoHouse.order.dto;

import lombok.*;
import java.math.BigDecimal;

/**
 * DTO de request para Payment - sin id ni timestamps (para POST)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private String paymentMethod;
    private BigDecimal amount;
    private String transactionId;
}

