package com.EcoHouse.payment.repository;

import com.EcoHouse.order.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Buscar pagos en un rango de fechas
    List<Payment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Buscar pagos por fecha específica
    List<Payment> findByPaymentDate(LocalDateTime paymentDate);
}

