package com.EcoHouse.order.mapper;

import com.EcoHouse.order.dto.PaymentResponse;
import com.EcoHouse.order.dto.PaymentRequest;
import com.EcoHouse.order.model.Payment;
import com.EcoHouse.order.model.PaymentStatus;

public class PaymentMapper {

    public static PaymentResponse toDto(Payment payment) {
        if (payment == null) return null;

        return PaymentResponse.builder()
                .id(payment.getId())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus() != null ? payment.getStatus().name() : null)
                .amount(payment.getAmount())
                .transactionId(payment.getTransactionId())
                .paymentDate(payment.getPaymentDate())
                .build();
    }

    public static Payment toEntity(PaymentRequest request) {
        if (request == null) return null;

        Payment payment = new Payment();
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setAmount(request.getAmount());
        payment.setTransactionId(request.getTransactionId());
        payment.setStatus(PaymentStatus.PENDING); // Por defecto
        return payment;
    }
}
