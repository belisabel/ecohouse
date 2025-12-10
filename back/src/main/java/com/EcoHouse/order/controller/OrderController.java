package com.EcoHouse.order.controller;

import com.EcoHouse.order.dto.OrderResponse;
import com.EcoHouse.order.dto.OrderRequest;
import com.EcoHouse.order.mapper.OrderMapper;
import com.EcoHouse.order.model.Order;
import com.EcoHouse.order.model.OrderStatus;
import com.EcoHouse.order.services.IOrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:3000"
})
@Tag(name = "Orders", description = "API para gestión de órdenes/pedidos")
public class OrderController {

    private final IOrderService orderService;

    /**
     * Obtener todas las órdenes con paginación
     */
    @Operation(summary = "Obtener todas las órdenes con paginación",
               description = "Lista todas las órdenes del sistema ordenadas por ID")
    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getAllOrders(
            @Parameter(description = "Número de página (inicia en 0)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página")
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        Page<Order> ordersPage = orderService.getAllOrders(pageable);
        Page<OrderResponse> responsePage = ordersPage.map(OrderMapper::toDTO);

        return ResponseEntity.ok(responsePage);
    }

    /**
     * Crear una nueva orden a partir del carrito de un cliente
     */
    @Operation(summary = "Crear nueva orden", description = "Crea una nueva orden a partir del carrito del cliente")
    @PostMapping("/customer/{customerId}")
    public ResponseEntity<OrderResponse> createOrder(@PathVariable Long customerId) {
        Order order = orderService.createOrder(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderMapper.toDTO(order));
    }

    /**
     * Obtener una orden por su ID
     */
    @Operation(summary = "Obtener orden por ID", description = "Obtiene los detalles completos de una orden")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(OrderMapper.toDTO(order));
    }

    /**
     * Obtener todas las órdenes de un cliente
     */
    @Operation(summary = "Obtener órdenes de un cliente", description = "Lista todas las órdenes de un cliente específico")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByCustomer(@PathVariable Long customerId) {
        List<OrderResponse> orders = orderService.getOrdersByCustomer(customerId)
                .stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    /**
     * Actualizar el estado de una orden
     */
    @Operation(summary = "Actualizar estado de orden", description = "Cambia el estado de una orden (PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED, REFUNDED)")
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {
        Order updated = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(OrderMapper.toDTO(updated));
    }

    /**
     * Calcular el impacto ambiental de una orden
     */
    @Operation(summary = "Calcular impacto ambiental", description = "Calcula y actualiza la huella de carbono y CO2 ahorrado de la orden")
    @PostMapping("/{orderId}/calculate-impact")
    public ResponseEntity<OrderResponse> calculateImpact(@PathVariable Long orderId) {
        Order order = orderService.calculateImpact(orderId);
        return ResponseEntity.ok(OrderMapper.toDTO(order));
    }

    /**
     * Cancelar una orden
     */
    @Operation(summary = "Cancelar orden", description = "Cancela una orden cambiando su estado a CANCELLED")
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId) {
        Order cancelled = orderService.updateOrderStatus(orderId, OrderStatus.CANCELLED);
        return ResponseEntity.ok(OrderMapper.toDTO(cancelled));
    }

    /**
     * Confirmar una orden (cambiar a PROCESSING)
     */
    @Operation(summary = "Confirmar orden", description = "Confirma una orden cambiando su estado a PROCESSING")
    @PostMapping("/{orderId}/confirm")
    public ResponseEntity<OrderResponse> confirmOrder(@PathVariable Long orderId) {
        Order confirmed = orderService.updateOrderStatus(orderId, OrderStatus.PROCESSING);
        return ResponseEntity.ok(OrderMapper.toDTO(confirmed));
    }

    /**
     * Marcar orden como enviada
     */
    @Operation(summary = "Marcar como enviada", description = "Marca la orden como enviada (SHIPPED)")
    @PostMapping("/{orderId}/ship")
    public ResponseEntity<OrderResponse> shipOrder(@PathVariable Long orderId) {
        Order shipped = orderService.updateOrderStatus(orderId, OrderStatus.SHIPPED);
        return ResponseEntity.ok(OrderMapper.toDTO(shipped));
    }

    /**
     * Marcar orden como entregada (solo admin/vendedor)
     */
    @Operation(summary = "Marcar como entregada", description = "Marca la orden como entregada (DELIVERED) - uso administrativo")
    @PostMapping("/{orderId}/deliver")
    public ResponseEntity<OrderResponse> deliverOrder(@PathVariable Long orderId) {
        Order delivered = orderService.updateOrderStatus(orderId, OrderStatus.DELIVERED);
        return ResponseEntity.ok(OrderMapper.toDTO(delivered));
    }

    /**
     * Confirmar recepción de la orden (por el cliente)
     */
    @Operation(summary = "Confirmar recepción", description = "El cliente confirma que recibió su orden")
    @PostMapping("/{orderId}/confirm-delivery")
    public ResponseEntity<OrderResponse> confirmDelivery(@PathVariable Long orderId) {
        Order delivered = orderService.confirmDelivery(orderId);
        return ResponseEntity.ok(OrderMapper.toDTO(delivered));
    }
}
