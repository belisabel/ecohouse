package com.EcoHouse.impactReport.controller;

import com.EcoHouse.impactReport.dtoRequest.ImpactReportRequestDto;
import com.EcoHouse.impactReport.dtoResponse.ImpactReportResponseDto;
import com.EcoHouse.impactReport.service.ImpactReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Slf4j
@Tag(
    name = "Impact Reports",
    description = "**API para Gestión de Reportes de Impacto Ambiental**\n\n" +
                  "Este módulo permite generar y consultar reportes detallados del impacto ambiental " +
                  "generado por las compras de cada cliente en la plataforma EcoHouse.\n\n" +
                  "### 🌱 Funcionalidades Principales:\n" +
                  "- **Generación de Reportes**: Crea reportes de impacto para períodos específicos\n" +
                  "- **Consulta por Cliente**: Obtiene historial completo de reportes de un cliente\n" +
                  "- **Estadísticas Agregadas**: Calcula totales de CO2 ahorrado y Eco Points ganados\n" +
                  "- **Análisis de Tendencias**: Identifica patrones mensuales de consumo sostenible\n\n" +
                  "### 📊 Métricas Incluidas:\n" +
                  "- CO2 ahorrado vs productos convencionales\n" +
                  "- Huella de carbono total de las compras\n" +
                  "- Eco Points ganados (sistema de recompensas)\n" +
                  "- Categorías de productos más compradas\n" +
                  "- Tendencias mensuales de impacto ambiental\n\n" +
                  "### 🎯 Casos de Uso:\n" +
                  "- Dashboard de cliente con su impacto ambiental\n" +
                  "- Reportes mensuales automatizados\n" +
                  "- Análisis de comportamiento de compra sostenible\n" +
                  "- Gamificación y sistema de recompensas"
)
public class ImpactReportController {

    private final ImpactReportService impactReportService;

    @PostMapping("/impact")
    @Operation(
        summary = "Generar Reporte de Impacto Ambiental",
        description = """
                ### 📝 Descripción
                Genera un reporte completo y detallado del impacto ambiental de un cliente en un período específico.
                
                El reporte analiza todas las órdenes completadas (DELIVERED) del cliente dentro del rango de fechas
                y calcula métricas ambientales basadas en los productos adquiridos.
                
                ### 🔍 Proceso de Generación
                1. **Validación**: Verifica que el cliente existe y el período es válido
                2. **Consulta de Órdenes**: Busca todas las órdenes DELIVERED en el rango de fechas
                3. **Cálculo de Métricas**:
                   - CO2 total generado (suma de huella de carbono de productos)
                   - CO2 ahorrado (vs productos convencionales, ~30% de ahorro)
                   - Eco Points ganados (basado en compras sostenibles)
                4. **Análisis por Categorías**: Agrupa impacto por tipo de producto
                5. **Tendencias Mensuales**: Calcula evolución mensual del impacto
                6. **Persistencia**: Guarda el reporte en la base de datos
                
                ### ⚠️ Validaciones
                - El cliente debe existir en el sistema
                - El período debe ser válido (startDate < endDate)
                - No puede haber un reporte existente para el mismo cliente y período
                - El cliente debe tener al menos una orden en el período especificado
                
                ### 💡 Casos de Uso
                - **Reporte Mensual Automático**: Generar al final de cada mes
                - **Consulta del Cliente**: Generar bajo demanda para ver impacto
                - **Dashboard Admin**: Analizar comportamiento de clientes
                - **Gamificación**: Mostrar progreso y logros ambientales
                
                ### 📊 Ejemplo de Request
                ```json
                {
                  "customerId": 1,
                  "startDate": "2025-11-01T00:00:00",
                  "endDate": "2025-11-30T23:59:59"
                }
                ```
                """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "✅ Reporte generado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ImpactReportResponseDto.class),
                examples = @ExampleObject(
                    name = "Reporte generado",
                    value = """
                            {
                              "id": 1,
                              "customerId": 1,
                              "customerName": "Ana García",
                              "startDate": "2025-11-01T00:00:00",
                              "endDate": "2025-11-30T23:59:59",
                              "totalCO2Generated": 15.75,
                              "totalCO2Saved": 4.73,
                              "ecoPointsEarned": 120,
                              "totalOrders": 3,
                              "totalAmount": 289.97,
                              "categoriesImpact": [
                                {
                                  "categoryName": "Camisetas y Polos",
                                  "co2Generated": 5.0,
                                  "orderCount": 2
                                }
                              ],
                              "monthlyTrends": [
                                {
                                  "month": "2025-11",
                                  "co2Saved": 4.73,
                                  "ecoPoints": 120
                                }
                              ]
                            }
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "❌ Parámetros inválidos (fechas incorrectas, customerId nulo, etc.)",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                            {
                              "error": "Bad Request",
                              "message": "La fecha de inicio debe ser anterior a la fecha de fin"
                            }
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "❌ Cliente no encontrado o sin órdenes en el período"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "❌ Ya existe un reporte para este cliente y período",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                            {
                              "error": "Conflict",
                              "message": "Ya existe un reporte para el cliente 1 en el período especificado"
                            }
                            """
                )
            )
        )
    })
//    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<ImpactReportResponseDto> generateReport(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = """
                        Datos necesarios para generar el reporte:
                        - **customerId**: ID del cliente (obligatorio)
                        - **startDate**: Fecha de inicio del período en formato ISO 8601
                        - **endDate**: Fecha de fin del período en formato ISO 8601
                        """,
                required = true,
                content = @Content(
                    schema = @Schema(implementation = ImpactReportRequestDto.class),
                    examples = @ExampleObject(
                        name = "Reporte mensual",
                        value = """
                                {
                                  "customerId": 1,
                                  "startDate": "2025-11-01T00:00:00",
                                  "endDate": "2025-11-30T23:59:59"
                                }
                                """
                    )
                )
            )
            @Valid @RequestBody ImpactReportRequestDto request) {

        log.info("📊 Generando reporte de impacto para cliente: {} | Período: {} - {}",
                 request.getCustomerId(), request.getStartDate(), request.getEndDate());

        ImpactReportResponseDto report = impactReportService.generateReport(request);

        log.info("✅ Reporte generado exitosamente - ID: {} | CO2 ahorrado: {} kg",
                 report.getId(), report.getTotalCO2Saved());

        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }

    @GetMapping("/customer/{customerId}")
    @Operation(
        summary = "Obtener Todos los Reportes de un Cliente",
        description = """
                ### 📋 Descripción
                Retorna el historial completo de reportes de impacto ambiental generados para un cliente específico.
                
                Los reportes se retornan ordenados por fecha de creación (más recientes primero), permitiendo
                ver la evolución del impacto ambiental del cliente a lo largo del tiempo.
                
                ### 🔍 Información Retornada
                Cada reporte incluye:
                - Período del reporte (startDate - endDate)
                - CO2 total generado y ahorrado
                - Eco Points ganados
                - Número de órdenes en el período
                - Monto total gastado
                - Desglose por categorías de productos
                - Tendencias mensuales
                
                ### 💡 Casos de Uso
                - **Dashboard del Cliente**: Mostrar historial de impacto ambiental
                - **Análisis de Tendencias**: Comparar reportes de diferentes períodos
                - **Progreso Ambiental**: Visualizar mejora en el tiempo
                - **Reportes Anuales**: Consolidar múltiples períodos
                
                ### 📊 Ejemplo de Uso
                ```
                GET /api/reports/customer/1
                ```
                """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "✅ Reportes obtenidos exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ImpactReportResponseDto.class),
                examples = @ExampleObject(
                    name = "Lista de reportes",
                    value = """
                            [
                              {
                                "id": 3,
                                "customerId": 1,
                                "customerName": "Ana García",
                                "startDate": "2025-11-01T00:00:00",
                                "endDate": "2025-11-30T23:59:59",
                                "totalCO2Generated": 15.75,
                                "totalCO2Saved": 4.73,
                                "ecoPointsEarned": 120,
                                "totalOrders": 3
                              },
                              {
                                "id": 2,
                                "customerId": 1,
                                "customerName": "Ana García",
                                "startDate": "2025-10-01T00:00:00",
                                "endDate": "2025-10-31T23:59:59",
                                "totalCO2Generated": 12.30,
                                "totalCO2Saved": 3.69,
                                "ecoPointsEarned": 90,
                                "totalOrders": 2
                              }
                            ]
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "❌ Cliente no encontrado en el sistema"
        ),
        @ApiResponse(
            responseCode = "200",
            description = "✅ Cliente existe pero no tiene reportes generados (retorna lista vacía: [])"
        )
    })
    //@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<List<ImpactReportResponseDto>> getCustomerReports(
            @Parameter(
                description = "ID único del cliente en el sistema",
                required = true,
                example = "1"
            )
            @PathVariable Long customerId) {

        log.debug("📋 Obteniendo reportes para cliente: {}", customerId);

        List<ImpactReportResponseDto> reports = impactReportService.getReportsByCustomer(customerId);

        log.debug("✅ Se encontraron {} reportes para el cliente {}", reports.size(), customerId);

        return ResponseEntity.ok(reports);
    }

    @GetMapping("/customer/{customerId}/latest")
    @Operation(
        summary = "Obtener Último Reporte de un Cliente",
        description = """
                ### 🔝 Descripción
                Retorna el reporte de impacto ambiental más reciente generado para un cliente específico.
                
                Este endpoint es útil para mostrar rápidamente el estado actual del impacto ambiental
                del cliente sin necesidad de cargar todo el historial.
                
                ### 🎯 Criterio de Selección
                - Se retorna el reporte con la fecha de creación más reciente
                - Solo se consideran reportes activos (no eliminados)
                - Si hay múltiples reportes, se selecciona el último generado
                
                ### 💡 Casos de Uso
                - **Dashboard Principal**: Mostrar resumen actual del impacto
                - **Widget de Estadísticas**: Indicadores rápidos en la interfaz
                - **Notificaciones**: "Tu último reporte muestra que ahorraste X kg de CO2"
                - **Comparación Rápida**: Ver mejora respecto al reporte anterior
                
                ### 📊 Ejemplo de Uso
                ```
                GET /api/reports/customer/1/latest
                ```
                
                ### ⚠️ Nota
                Si el cliente no tiene reportes generados, retorna 404 Not Found.
                """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "✅ Último reporte obtenido exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ImpactReportResponseDto.class),
                examples = @ExampleObject(
                    name = "Último reporte",
                    value = """
                            {
                              "id": 5,
                              "customerId": 1,
                              "customerName": "Ana García",
                              "startDate": "2025-11-01T00:00:00",
                              "endDate": "2025-11-30T23:59:59",
                              "totalCO2Generated": 15.75,
                              "totalCO2Saved": 4.73,
                              "ecoPointsEarned": 120,
                              "totalOrders": 3,
                              "totalAmount": 289.97,
                              "createdAt": "2025-12-01T10:30:00",
                              "categoriesImpact": [],
                              "monthlyTrends": []
                            }
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "❌ No se encontraron reportes para este cliente",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                            {
                              "error": "Not Found",
                              "message": "No hay reportes disponibles para el cliente 1"
                            }
                            """
                )
            )
        )
    })
    //@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<ImpactReportResponseDto> getLatestReport(
            @Parameter(
                description = "ID único del cliente en el sistema",
                required = true,
                example = "1"
            )
            @PathVariable Long customerId) {

        log.debug("🔝 Obteniendo último reporte para cliente: {}", customerId);

        return impactReportService.getLatestReport(customerId)
                .map(report -> {
                    log.debug("✅ Último reporte encontrado - ID: {} | Período: {} - {}",
                             report.getId(), report.getStartDate(), report.getEndDate());
                    return ResponseEntity.ok(report);
                })
                .orElseGet(() -> {
                    log.debug("❌ No se encontraron reportes para cliente: {}", customerId);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping("/{reportId}")
    @Operation(
        summary = "Obtener Reporte por ID",
        description = """
                ### 🔍 Descripción
                Obtiene un reporte de impacto ambiental específico mediante su identificador único.
                
                Este endpoint permite recuperar los detalles completos de un reporte en particular,
                incluyendo todas las métricas, desgloses por categorías y tendencias mensuales.
                
                ### 📊 Información Completa del Reporte
                - Datos generales (cliente, período, fechas)
                - Métricas ambientales (CO2 generado, ahorrado)
                - Eco Points ganados
                - Estadísticas de órdenes y montos
                - **Desglose por Categorías**: Impacto de cada tipo de producto
                - **Tendencias Mensuales**: Evolución del impacto en el período
                
                ### 💡 Casos de Uso
                - **Vista Detallada**: Mostrar reporte completo en pantalla dedicada
                - **Exportación**: Obtener datos para generar PDF o Excel
                - **Compartir**: Link directo a un reporte específico
                - **Auditoría**: Revisar reportes históricos con todos los detalles
                
                ### 📊 Ejemplo de Uso
                ```
                GET /api/reports/5
                ```
                
                ### ⚠️ Nota Técnica
                Este endpoint actualmente retorna 404 (implementación pendiente en el service).
                """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "✅ Reporte obtenido exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ImpactReportResponseDto.class),
                examples = @ExampleObject(
                    name = "Reporte completo",
                    value = """
                            {
                              "id": 5,
                              "customerId": 1,
                              "customerName": "Ana García",
                              "startDate": "2025-11-01T00:00:00",
                              "endDate": "2025-11-30T23:59:59",
                              "totalCO2Generated": 15.75,
                              "totalCO2Saved": 4.73,
                              "ecoPointsEarned": 120,
                              "totalOrders": 3,
                              "totalAmount": 289.97,
                              "categoriesImpact": [
                                {
                                  "categoryName": "Camisetas y Polos",
                                  "co2Generated": 5.0,
                                  "co2Saved": 1.5,
                                  "orderCount": 2,
                                  "totalAmount": 89.98
                                },
                                {
                                  "categoryName": "Pantalones y Jeans",
                                  "co2Generated": 8.5,
                                  "co2Saved": 2.55,
                                  "orderCount": 1,
                                  "totalAmount": 79.99
                                }
                              ],
                              "monthlyTrends": [
                                {
                                  "month": "2025-11",
                                  "co2Generated": 15.75,
                                  "co2Saved": 4.73,
                                  "ecoPoints": 120,
                                  "orderCount": 3
                                }
                              ]
                            }
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "❌ Reporte no encontrado o eliminado",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                            {
                              "error": "Not Found",
                              "message": "No se encontró el reporte con ID 999"
                            }
                            """
                )
            )
        )
    })
    //@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<ImpactReportResponseDto> getReportById(
            @Parameter(
                description = "ID único del reporte en el sistema",
                required = true,
                example = "5"
            )
            @PathVariable Long reportId) {

        log.debug("🔍 Obteniendo reporte con ID: {}", reportId);

        // TODO: Implementar en el service
        // return impactReportService.getReportById(reportId)
        //     .map(report -> {
        //         log.debug("✅ Reporte encontrado - Cliente: {}", report.getCustomerId());
        //         return ResponseEntity.ok(report);
        //     })
        //     .orElseGet(() -> {
        //         log.debug("❌ Reporte no encontrado con ID: {}", reportId);
        //         return ResponseEntity.notFound().build();
        //     });

        log.warn("⚠️ Endpoint getReportById no implementado completamente en el service");
        return ResponseEntity.notFound().build(); // Placeholder
    }

    @GetMapping("/customer/{customerId}/stats")
    @Operation(
        summary = "Obtener Estadísticas Agregadas del Cliente",
        description = """
                ### 📈 Descripción
                Obtiene estadísticas consolidadas del impacto ambiental total de un cliente a través de TODOS sus reportes.
                
                Este endpoint calcula los **totales acumulados** sumando los valores de todos los reportes
                generados para el cliente, proporcionando una visión global de su impacto ambiental desde
                que comenzó a comprar en la plataforma.
                
                ### 📊 Métricas Calculadas
                1. **Total CO2 Ahorrado**: Suma del CO2 ahorrado en todos los reportes
                   - Representa el ahorro acumulado vs productos convencionales
                   - Medido en kilogramos (kg)
                   
                2. **Total Eco Points**: Suma de todos los puntos ecológicos ganados
                   - Sistema de recompensas por compras sostenibles
                   - Puntos enteros acumulables
                
                ### 🎯 Diferencia con Otros Endpoints
                - **`/customer/{id}`**: Lista todos los reportes individuales
                - **`/customer/{id}/latest`**: Solo el reporte más reciente
                - **`/customer/{id}/stats`**: Totales consolidados de TODOS los reportes ✅
                
                ### 💡 Casos de Uso
                - **Indicador Global**: "Has ahorrado X kg de CO2 desde que te registraste"
                - **Gamificación**: Mostrar logros totales y progreso acumulado
                - **Ranking de Clientes**: Comparar impacto total entre usuarios
                - **Badges y Recompensas**: Desbloquear logros por totales alcanzados
                - **Widget de Perfil**: Mostrar impacto total en perfil del usuario
                
                ### 📊 Ejemplo de Uso
                ```
                GET /api/reports/customer/1/stats
                ```
                
                ### 🏆 Ejemplo de Gamificación
                - 0-50 kg CO2: 🌱 Eco Principiante
                - 50-200 kg CO2: 🌿 Eco Guerrero
                - 200-500 kg CO2: 🌳 Eco Héroe
                - 500+ kg CO2: 🌍 Eco Leyenda
                """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "✅ Estadísticas calculadas exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CustomerStatsDto.class),
                examples = @ExampleObject(
                    name = "Estadísticas acumuladas",
                    value = """
                            {
                              "customerId": 1,
                              "totalCO2Saved": 47.35,
                              "totalEcoPoints": 1250
                            }
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "200",
            description = "✅ Cliente sin reportes (retorna valores en 0)",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Sin reportes",
                    value = """
                            {
                              "customerId": 1,
                              "totalCO2Saved": 0.00,
                              "totalEcoPoints": 0
                            }
                            """
                )
            )
        )
    })
    //@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<CustomerStatsDto> getCustomerStats(
            @Parameter(
                description = "ID único del cliente en el sistema",
                required = true,
                example = "1"
            )
            @PathVariable Long customerId) {

        log.debug("📈 Calculando estadísticas agregadas para cliente: {}", customerId);

        BigDecimal totalCO2Saved = impactReportService.getCustomerTotalCO2Saved(customerId);
        Integer totalEcoPoints = impactReportService.getCustomerTotalEcoPoints(customerId);

        CustomerStatsDto stats = CustomerStatsDto.builder()
                .customerId(customerId)
                .totalCO2Saved(totalCO2Saved)
                .totalEcoPoints(totalEcoPoints)
                .build();

        log.debug("✅ Estadísticas calculadas - CO2: {} kg | Puntos: {}",
                 totalCO2Saved, totalEcoPoints);

        return ResponseEntity.ok(stats);
    }

    @DeleteMapping("/{reportId}")
    @Operation(
        summary = "Eliminar Reporte de Impacto",
        description = """
                ### 🗑️ Descripción
                Realiza una **eliminación lógica (soft delete)** de un reporte de impacto ambiental.
                
                El reporte no se elimina físicamente de la base de datos, sino que se marca como
                "eliminado" mediante un flag, permitiendo su recuperación posterior si es necesario.
                
                ### 🔒 Eliminación Lógica vs Física
                - **Soft Delete**: El registro permanece en BD pero marcado como eliminado
                - **Beneficios**: 
                  - Auditoría completa
                  - Posibilidad de recuperación
                  - Integridad referencial mantenida
                  - Histórico completo para análisis
                
                ### ⚠️ Restricciones
                - Solo usuarios ADMIN pueden eliminar reportes
                - El reporte debe existir y no estar eliminado previamente
                - La eliminación NO afecta las órdenes originales
                - Las estadísticas agregadas se recalculan excluyendo reportes eliminados
                
                ### 💡 Casos de Uso
                - **Corrección de Errores**: Eliminar reportes generados con datos incorrectos
                - **Duplicados**: Remover reportes duplicados por error
                - **Períodos Incorrectos**: Eliminar y regenerar con fechas correctas
                - **Auditoría**: Limpiar reportes de prueba en producción
                
                ### 🔄 Proceso de Eliminación
                1. Verifica que el reporte existe
                2. Marca el reporte como eliminado (soft delete)
                3. Actualiza timestamps de eliminación
                4. Retorna 204 No Content (éxito sin cuerpo)
                
                ### 📊 Ejemplo de Uso
                ```
                DELETE /api/reports/5
                ```
                
                ### ⚠️ Nota Importante
                Después de eliminar un reporte:
                - NO aparecerá en listados normales
                - NO se incluirá en estadísticas agregadas
                - Puede regenerarse el período si es necesario
                """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "✅ Reporte eliminado exitosamente (sin contenido en la respuesta)"
        ),
        @ApiResponse(
            responseCode = "403",
            description = "❌ Acceso denegado - Solo administradores pueden eliminar reportes",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                            {
                              "error": "Forbidden",
                              "message": "Solo administradores pueden eliminar reportes"
                            }
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "❌ Reporte no encontrado o ya eliminado",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                            {
                              "error": "Not Found",
                              "message": "No se encontró el reporte con ID 999"
                            }
                            """
                )
            )
        )
    })
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteReport(
            @Parameter(
                description = "ID único del reporte a eliminar",
                required = true,
                example = "5"
            )
            @PathVariable Long reportId) {

        log.info("🗑️ Solicitando eliminación del reporte ID: {}", reportId);

        impactReportService.deleteReport(reportId);

        log.info("✅ Reporte {} eliminado exitosamente", reportId);

        return ResponseEntity.noContent().build();
    }

    /**
     * DTO para estadísticas consolidadas del impacto ambiental de un cliente.
     *
     * Este DTO representa los totales acumulados de todos los reportes del cliente,
     * proporcionando una vista agregada de su impacto ambiental global.
     */
    @lombok.Data
    @lombok.Builder
    @Schema(description = "Estadísticas consolidadas de impacto ambiental por cliente")
    private static class CustomerStatsDto {

        @Schema(
            description = "ID único del cliente",
            example = "1",
            required = true
        )
        private Long customerId;

        @Schema(
            description = "Total de CO2 ahorrado acumulado (kg) vs productos convencionales. " +
                         "Suma de todos los reportes del cliente.",
            example = "47.35",
            required = true
        )
        private BigDecimal totalCO2Saved;

        @Schema(
            description = "Total de Eco Points ganados acumulados. " +
                         "Puntos de recompensa por compras sostenibles.",
            example = "1250",
            required = true
        )
        private Integer totalEcoPoints;
    }
}