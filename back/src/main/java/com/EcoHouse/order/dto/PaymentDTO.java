package com.EcoHouse.order.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {

    private Long id;
    private String paymentMethod;
    private String status;
    private BigDecimal amount;
    private String transactionId;
    private Date paymentDate;
}

