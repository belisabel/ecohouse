package com.EcoHouse.order.controller;

import com.EcoHouse.order.service.OrderDataLoaderService;
import com.EcoHouse.order.model.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:3000"
})
@Tag(name = "Z - Admin Tools", description = "🔧 Herramientas administrativas para gestión de datos de ejemplo y testing")
public class OrderDataLoaderController {

    private final OrderDataLoaderService orderDataLoaderService;

    /**
     * Cargar 10 órdenes de ejemplo
     */
    @PostMapping("/load-sample-orders")
    @Operation(summary = "Cargar órdenes de ejemplo",
               description = "Carga 10 órdenes completas de ejemplo con estado DELIVERED")
    public ResponseEntity<Map<String, Object>> loadSampleOrders() {
        List<Order> orders = orderDataLoaderService.loadSampleOrders();

        return ResponseEntity.ok(Map.of(
                "message", "Órdenes de ejemplo cargadas exitosamente",
                "count", orders.size(),
                "orderNumbers", orders.stream().map(Order::getOrderNumber).toList()
        ));
    }

    /**
     * Eliminar órdenes de ejemplo
     */
    @DeleteMapping("/delete-sample-orders")
    @Operation(summary = "Eliminar órdenes de ejemplo",
               description = "Elimina todas las órdenes que empiezan con ORD-SAMPLE-")
    public ResponseEntity<Map<String, Object>> deleteSampleOrders() {
        int deletedCount = orderDataLoaderService.deleteSampleOrders();

        return ResponseEntity.ok(Map.of(
                "message", "Órdenes de ejemplo eliminadas exitosamente",
                "count", deletedCount
        ));
    }
}

