package com.EcoHouse.order.mapper;

import com.EcoHouse.order.dto.PaymentDTO;
import com.EcoHouse.order.model.Payment;
import com.EcoHouse.order.model.PaymentStatus;

public class PaymentMapper {

    public static PaymentDTO toDto(Payment payment) {
        if (payment == null) return null;

        return PaymentDTO.builder()
                .id(payment.getId())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus() != null ? payment.getStatus().name() : null)
                .amount(payment.getAmount())
                .transactionId(payment.getTransactionId())
                .paymentDate(payment.getPaymentDate())
                .build();
    }

    public static Payment toEntity(PaymentDTO dto) {
        if (dto == null) return null;

        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setPaymentMethod(dto.getPaymentMethod());

        // Conversión segura del enum desde String
        if (dto.getStatus() != null && !dto.getStatus().isBlank()) {
            payment.setStatus(PaymentStatus.valueOf(dto.getStatus()));
        } else {
            payment.setStatus(null);
        }

        payment.setAmount(dto.getAmount());
        payment.setTransactionId(dto.getTransactionId());
        payment.setPaymentDate(dto.getPaymentDate());

        return payment;
    }
}
