package com.EcoHouse.payment.mapper;

import com.EcoHouse.order.model.Payment;
import com.EcoHouse.payment.dto.PaymentCreateDTO;
import com.EcoHouse.payment.dto.PaymentDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PaymentMapper {

    /**
     * Convierte una entidad Payment a PaymentDTO
     */
    public PaymentDTO toDTO(Payment payment) {
        if (payment == null) {
            return null;
        }

        return PaymentDTO.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .paymentDate(payment.getPaymentDate())
                .build();
    }

    /**
     * Convierte un PaymentCreateDTO a entidad Payment
     */
    public Payment toEntity(PaymentCreateDTO createDTO) {
        if (createDTO == null) {
            return null;
        }

        return Payment.builder()
                .amount(createDTO.getAmount())
                .paymentDate(createDTO.getPaymentDate() != null ?
                        createDTO.getPaymentDate() : LocalDateTime.now())
                .build();
    }

    /**
     * Actualiza una entidad Payment existente con datos de PaymentCreateDTO
     */
    public void updateEntity(Payment payment, PaymentCreateDTO createDTO) {
        if (payment == null || createDTO == null) {
            return;
        }

        payment.setAmount(createDTO.getAmount());
        if (createDTO.getPaymentDate() != null) {
            payment.setPaymentDate(createDTO.getPaymentDate());
        }
    }
}

