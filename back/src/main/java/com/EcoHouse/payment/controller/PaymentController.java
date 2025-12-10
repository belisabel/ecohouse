package com.EcoHouse.payment.controller;

import com.EcoHouse.payment.dto.PaymentCreateDTO;
import com.EcoHouse.payment.dto.PaymentDTO;
import com.EcoHouse.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "API para gestión de pagos")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Crear un nuevo pago asociado a una orden
     */
    @PostMapping
    @Operation(summary = "Crear un nuevo pago",
               description = "Crea un nuevo registro de pago y lo asocia a una orden específica. " +
                           "La orden no debe tener un pago previo asociado.")
    public ResponseEntity<PaymentDTO> createPayment(@Valid @RequestBody PaymentCreateDTO createDTO) {
        PaymentDTO createdPayment = paymentService.createPayment(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
    }

    /**
     * Obtener un pago por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener pago por ID", description = "Retorna un pago específico por su ID")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) {
        PaymentDTO payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    /**
     * Obtener todos los pagos
     */
    @GetMapping
    @Operation(summary = "Obtener todos los pagos", description = "Retorna una lista de todos los pagos")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    /**
     * Obtener pagos por rango de fechas
     */
    @GetMapping("/by-date-range")
    @Operation(summary = "Obtener pagos por rango de fechas",
               description = "Retorna pagos realizados entre dos fechas")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<PaymentDTO> payments = paymentService.getPaymentsByDateRange(startDate, endDate);
        return ResponseEntity.ok(payments);
    }

    /**
     * Actualizar un pago
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un pago", description = "Actualiza los datos de un pago existente")
    public ResponseEntity<PaymentDTO> updatePayment(
            @PathVariable Long id,
            @Valid @RequestBody PaymentCreateDTO updateDTO) {
        PaymentDTO updatedPayment = paymentService.updatePayment(id, updateDTO);
        return ResponseEntity.ok(updatedPayment);
    }

    /**
     * Eliminar un pago
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un pago", description = "Elimina un pago del sistema")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}

