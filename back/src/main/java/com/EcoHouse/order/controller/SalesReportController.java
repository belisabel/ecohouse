package com.EcoHouse.order.controller;

import com.EcoHouse.order.service.SalesReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
@Tag(
    name = "Sales Reports",
    description = "API para reportes y estadísticas de ventas. " +
                  "Permite consultar información sobre ventas totales, ventas por cliente, " +
                  "y estadísticas completas de rendimiento del negocio. " +
                  "Todos los montos están en Euros (EUR)."
)
public class SalesReportController {

    private final SalesReportService salesReportService;

    /**
     * Obtener el total de ventas de todas las órdenes completadas
     */
    @GetMapping("/total")
    @Operation(
        summary = "Obtener total de ventas",
        description = "Calcula y retorna el monto total de todas las ventas completadas (estado DELIVERED). " +
                      "Este endpoint suma el campo 'totalAmount' de todas las órdenes con estado DELIVERED. " +
                      "Es útil para obtener una visión general rápida del volumen de ventas del negocio."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Total de ventas calculado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Map.class),
                examples = @ExampleObject(
                    name = "Ejemplo de respuesta",
                    value = "{\n" +
                           "  \"totalSales\": 2829.47,\n" +
                           "  \"currency\": \"EUR\"\n" +
                           "}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor al calcular las ventas"
        )
    })
    public ResponseEntity<Map<String, Object>> getTotalSales() {
        BigDecimal totalSales = salesReportService.getTotalSales();
        return ResponseEntity.ok(Map.of(
                "totalSales", totalSales,
                "currency", "EUR"
        ));
    }

    /**
     * Obtener el total de ventas por rango de fechas
     */
    @GetMapping("/total-by-date-range")
    @Operation(
        summary = "Obtener ventas por rango de fechas",
        description = "Calcula el total de ventas de órdenes completadas (DELIVERED) dentro de un rango de fechas específico. " +
                      "Permite analizar el rendimiento de ventas en períodos personalizados (diario, semanal, mensual, etc.). " +
                      "Las fechas deben estar en formato ISO 8601: yyyy-MM-dd'T'HH:mm:ss"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ventas por rango de fechas calculadas exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Map.class),
                examples = @ExampleObject(
                    name = "Ejemplo de respuesta",
                    value = "{\n" +
                           "  \"startDate\": \"2025-11-01T00:00:00\",\n" +
                           "  \"endDate\": \"2025-12-09T23:59:59\",\n" +
                           "  \"totalSales\": 1856.43,\n" +
                           "  \"currency\": \"EUR\"\n" +
                           "}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Formato de fecha inválido o rango de fechas incorrecto"
        )
    })
    public ResponseEntity<Map<String, Object>> getTotalSalesByDateRange(
            @Parameter(
                description = "Fecha de inicio del rango (formato: yyyy-MM-dd'T'HH:mm:ss)",
                example = "2025-11-01T00:00:00",
                required = true
            )
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,

            @Parameter(
                description = "Fecha de fin del rango (formato: yyyy-MM-dd'T'HH:mm:ss)",
                example = "2025-12-09T23:59:59",
                required = true
            )
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        BigDecimal totalSales = salesReportService.getTotalSalesByDateRange(startDate, endDate);

        return ResponseEntity.ok(Map.of(
                "startDate", startDate,
                "endDate", endDate,
                "totalSales", totalSales,
                "currency", "EUR"
        ));
    }

    /**
     * Obtener ventas por cliente
     */
    @GetMapping("/by-customer")
    @Operation(
        summary = "Obtener ventas agrupadas por cliente",
        description = "Retorna un desglose del total de ventas para cada cliente que ha realizado compras completadas (DELIVERED). " +
                      "Los resultados están agrupados por nombre completo del cliente (firstName + lastName). " +
                      "Útil para identificar los clientes más valiosos y analizar patrones de compra."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ventas por cliente obtenidas exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Map.class),
                examples = @ExampleObject(
                    name = "Ejemplo de respuesta",
                    value = "{\n" +
                           "  \"Ana García\": 229.96,\n" +
                           "  \"Carlos López\": 389.97,\n" +
                           "  \"María Rodríguez\": 259.93,\n" +
                           "  \"Pedro Martínez\": 169.97,\n" +
                           "  \"Laura Sánchez\": 299.96\n" +
                           "}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno al procesar las ventas por cliente"
        )
    })
    public ResponseEntity<Map<String, BigDecimal>> getSalesByCustomer() {
        Map<String, BigDecimal> salesByCustomer = salesReportService.getSalesByCustomer();
        return ResponseEntity.ok(salesByCustomer);
    }

    /**
     * Obtener estadísticas completas de ventas
     */
    @GetMapping("/statistics")
    @Operation(
        summary = "Obtener estadísticas completas de ventas",
        description = "Retorna un conjunto completo de métricas clave del negocio basadas en órdenes completadas (DELIVERED). " +
                      "Incluye: " +
                      "• **totalSales**: Suma total de ventas en EUR " +
                      "• **totalOrders**: Número total de órdenes completadas " +
                      "• **averageOrderValue**: Valor promedio por orden (totalSales / totalOrders) " +
                      "• **currency**: Moneda de los montos (EUR) " +
                      "\n\nEste endpoint es ideal para dashboards y reportes ejecutivos."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Estadísticas calculadas exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Map.class),
                examples = @ExampleObject(
                    name = "Ejemplo de respuesta completa",
                    value = "{\n" +
                           "  \"totalSales\": 2829.47,\n" +
                           "  \"totalOrders\": 10,\n" +
                           "  \"averageOrderValue\": 282.95,\n" +
                           "  \"currency\": \"EUR\"\n" +
                           "}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error al calcular las estadísticas"
        )
    })
    public ResponseEntity<Map<String, Object>> getSalesStatistics() {
        Map<String, Object> statistics = salesReportService.getSalesStatistics();
        return ResponseEntity.ok(statistics);
    }

    /**
     * Obtener el total de pagos realizados
     */
    @GetMapping("/total-payments")
    @Operation(
        summary = "Obtener total de pagos procesados",
        description = "Calcula y retorna la suma de todos los pagos asociados a órdenes completadas (DELIVERED). " +
                      "Este valor debería coincidir con el total de ventas, ya que cada orden completada " +
                      "tiene un pago asociado con el mismo monto. " +
                      "Es útil para validación y conciliación de cuentas."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Total de pagos calculado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Map.class),
                examples = @ExampleObject(
                    name = "Ejemplo de respuesta",
                    value = "{\n" +
                           "  \"totalPayments\": 2829.47,\n" +
                           "  \"currency\": \"EUR\"\n" +
                           "}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error al calcular el total de pagos"
        )
    })
    public ResponseEntity<Map<String, Object>> getTotalPayments() {
        BigDecimal totalPayments = salesReportService.getTotalPayments();
        return ResponseEntity.ok(Map.of(
                "totalPayments", totalPayments,
                "currency", "EUR"
        ));
    }
}

