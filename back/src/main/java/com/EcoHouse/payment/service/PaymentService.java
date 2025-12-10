package com.EcoHouse.payment.service;

import com.EcoHouse.order.model.Payment;
import com.EcoHouse.payment.dto.PaymentCreateDTO;
import com.EcoHouse.payment.dto.PaymentDTO;
import com.EcoHouse.payment.mapper.PaymentMapper;
import com.EcoHouse.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    /**
     * Crear un nuevo pago
     */
    @Transactional
    public PaymentDTO createPayment(PaymentCreateDTO createDTO) {
        log.info("Creando nuevo pago con monto: {}", createDTO.getAmount());

        Payment payment = paymentMapper.toEntity(createDTO);
        Payment savedPayment = paymentRepository.save(payment);

        log.info("Pago creado exitosamente con ID: {}", savedPayment.getId());
        return paymentMapper.toDTO(savedPayment);
    }

    /**
     * Obtener un pago por ID
     */
    @Transactional(readOnly = true)
    public PaymentDTO getPaymentById(Long id) {
        log.info("Buscando pago con ID: {}", id);

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));

        return paymentMapper.toDTO(payment);
    }

    /**
     * Obtener todos los pagos
     */
    @Transactional(readOnly = true)
    public List<PaymentDTO> getAllPayments() {
        log.info("Obteniendo todos los pagos");

        return paymentRepository.findAll().stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener pagos por rango de fechas
     */
    @Transactional(readOnly = true)
    public List<PaymentDTO> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Buscando pagos entre {} y {}", startDate, endDate);

        return paymentRepository.findByPaymentDateBetween(startDate, endDate).stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Actualizar un pago
     */
    @Transactional
    public PaymentDTO updatePayment(Long id, PaymentCreateDTO updateDTO) {
        log.info("Actualizando pago con ID: {}", id);

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));

        paymentMapper.updateEntity(payment, updateDTO);
        Payment updatedPayment = paymentRepository.save(payment);

        log.info("Pago actualizado exitosamente");
        return paymentMapper.toDTO(updatedPayment);
    }

    /**
     * Eliminar un pago
     */
    @Transactional
    public void deletePayment(Long id) {
        log.info("Eliminando pago con ID: {}", id);

        if (!paymentRepository.existsById(id)) {
            throw new RuntimeException("Pago no encontrado con ID: " + id);
        }

        paymentRepository.deleteById(id);
        log.info("Pago eliminado exitosamente");
    }
}

