package com.EcoHouse.order.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO de respuesta para Payment - incluye id y timestamps autogenerados
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private Long id;
    private String paymentMethod;
    private String status;
    private BigDecimal amount;
    private String transactionId;
    private Date paymentDate;
}

