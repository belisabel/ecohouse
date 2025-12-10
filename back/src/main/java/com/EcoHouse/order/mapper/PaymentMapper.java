package com.EcoHouse.order.mapper;

import com.EcoHouse.order.dto.PaymentResponse;
import com.EcoHouse.order.dto.PaymentRequest;
import com.EcoHouse.order.model.Payment;

import java.time.LocalDateTime;

public class PaymentMapper {

    public static PaymentResponse toDto(Payment payment) {
        if (payment == null) return null;

        return PaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .paymentDate(payment.getPaymentDate())
                .build();
    }

    public static Payment toEntity(PaymentRequest request) {
        if (request == null) return null;

        return Payment.builder()
                .amount(request.getAmount())
                .paymentDate(request.getPaymentDate() != null ? request.getPaymentDate() : LocalDateTime.now())
                .build();
    }
}
