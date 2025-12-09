package com.EcoHouse.impactReport.service;

import com.EcoHouse.impactReport.dto.OrderDataDto;
import com.EcoHouse.impactReport.dtoRequest.ImpactReportRequestDto;
import com.EcoHouse.impactReport.dtoResponse.ImpactReportResponseDto;
import com.EcoHouse.impactReport.entities.ImpactReport;
import com.EcoHouse.impactReport.repository.ImpactReportRepository;
import com.EcoHouse.impactReport.mapper.ImpactReportMapper;
import com.EcoHouse.impactReport.service.ImpactCalculationService.ImpactMetrics;
import com.EcoHouse.order.model.Order;
import com.EcoHouse.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImpactReportService {

    private final ImpactReportRepository impactReportRepository;
    private final ImpactReportMapper mapper;
    private final ImpactCalculationService calculationService;
    private final OrderMappingService orderMappingService;
    private final OrderRepository orderRepository;

    @Transactional
    public ImpactReportResponseDto generateReport(ImpactReportRequestDto request) {
        log.info("Generating impact report for customer: {} from {} to {}",
                request.getCustomerId(), request.getStartDate(), request.getEndDate());

        // Validaciones
        validateRequest(request);
        checkIfReportAlreadyExists(request);

        // 1. Obtener órdenes del cliente en el rango de fechas
        Date startDate = convertToDate(request.getStartDate());
        Date endDate = convertToDate(request.getEndDate());

        List<Order> orders = orderRepository.findCompletedOrdersByCustomerAndDateRange(
                request.getCustomerId(),
                startDate,
                endDate
        );

        log.debug("Found {} orders for customer {} in date range", orders.size(), request.getCustomerId());

        // 2. Mapear órdenes a DTOs para procesamiento
        List<OrderDataDto> orderDataDtos = orderMappingService.mapOrdersToDto(orders);

        // 3. Calcular métricas de impacto
        ImpactMetrics metrics = calculationService.calculateImpactMetrics(orderDataDtos);

        // 4. Crear y guardar entidad ImpactReport
        ImpactReport report = ImpactReport.builder()
                .customerId(request.getCustomerId())
                .startDate(request.getStartDate() != null ? request.getStartDate().toLocalDate() : null)
                .endDate(request.getEndDate() != null ? request.getEndDate().toLocalDate() : null)
                .totalCO2Saved(metrics.getTotalCO2Saved())
                .totalCO2Footprint(metrics.getTotalCO2Footprint())
                .totalOrders(metrics.getTotalOrders())
                .ecoPointsEarned(metrics.getTotalEcoPoints())
                .totalAmountSpent(metrics.getTotalAmount())
                .sustainableChoicesCount(metrics.getSustainableChoicesCount())
                .reportType(request.getReportType())
                .sustainableProductIds(null) // Se enriquecerá si se solicita
                .categoryBreakdown(null)     // Se enriquecerá si se solicita
                .monthlyTrend(null)          // Se enriquecerá si se solicita
                .build();

        ImpactReport savedReport = impactReportRepository.save(report);
        log.info("Successfully generated impact report with ID: {}", savedReport.getId());

        // 5. Convertir a DTO response y enriquecer con cálculos adicionales
        ImpactReportResponseDto response = mapper.toResponseDto(savedReport);
        enrichResponseWithCalculations(response, metrics, orderDataDtos, request);

        return response;
    }

    @Transactional(readOnly = true)
    public List<ImpactReportResponseDto> getReportsByCustomer(Long customerId) {
        log.debug("Retrieving reports for customer: {}", customerId);

        List<ImpactReport> reports = impactReportRepository
                .findByCustomerIdAndIsActiveTrueOrderByCreatedAtDesc(customerId);

        return reports.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ImpactReportResponseDto> getLatestReport(Long customerId) {
        log.debug("Retrieving latest report for customer: {}", customerId);

        return impactReportRepository
                .findFirstByCustomerIdOrderByCreatedAtDesc(customerId)
                .map(mapper::toResponseDto);
    }

    @Transactional(readOnly = true)
    public BigDecimal getCustomerTotalCO2Saved(Long customerId) {
        return impactReportRepository.getTotalCO2SavedByCustomer(customerId);
    }

    @Transactional(readOnly = true)
    public Integer getCustomerTotalEcoPoints(Long customerId) {
        return impactReportRepository.getTotalEcoPointsByCustomer(customerId);
    }

    @Transactional
    public void deleteReport(Long reportId) {
        log.info("Soft deleting report with ID: {}", reportId);

        ImpactReport report = impactReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found with ID: " + reportId));

        report.setIsActive(false);
        impactReportRepository.save(report);
    }

    // Métodos privados
    private void validateRequest(ImpactReportRequestDto request) {
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new RuntimeException("Start date must be before end date");
        }

        if (request.getStartDate().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("Start date cannot be in the future");
        }

        long daysBetween = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());
        if (daysBetween > 365) {
            throw new RuntimeException("Date range cannot exceed 365 days");
        }
    }

    private void checkIfReportAlreadyExists(ImpactReportRequestDto request) {
        boolean exists = impactReportRepository.existsByCustomerAndPeriod(
                request.getCustomerId(),
                request.getReportType(),
                request.getStartDate() != null ? request.getStartDate().toLocalDate() : null,
                request.getEndDate() != null ? request.getEndDate().toLocalDate() : null
        );

        if (exists) {
            throw new RuntimeException("A report already exists for this customer and period");
        }
    }

    /**
     * Método auxiliar para convertir LocalDateTime a Date
     */
    private Date convertToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Enriquece la respuesta con cálculos adicionales
     */
    private void enrichResponseWithCalculations(
            ImpactReportResponseDto response,
            ImpactMetrics metrics,
            List<OrderDataDto> orders,
            ImpactReportRequestDto request
    ) {
        if (response == null || metrics == null) return;

        // Calcular promedio de CO2 por orden
        if (response.getTotalOrders() != null && response.getTotalOrders() > 0) {
            BigDecimal avgCO2 = metrics.getTotalCO2Footprint()
                    .divide(BigDecimal.valueOf(response.getTotalOrders()), 3, RoundingMode.HALF_UP);
            response.setAverageOrderCO2(avgCO2);

            BigDecimal avgOrderValue = metrics.getTotalAmount()
                    .divide(BigDecimal.valueOf(response.getTotalOrders()), 2, RoundingMode.HALF_UP);
            response.setAverageOrderValue(avgOrderValue);
        }

        // Calcular score de eficiencia ecológica (0-100)
        BigDecimal ecoScore = calculationService.calculateEcoEfficiencyScore(metrics);
        response.setEcoEfficiencyScore(ecoScore);

        // Determinar nivel de impacto
        response.setImpactLevel(calculationService.determineImpactLevel(ecoScore));

        // Calcular porcentaje de sostenibilidad
        if (response.getTotalOrders() != null && response.getTotalOrders() > 0
                && response.getSustainableChoicesCount() != null) {
            BigDecimal sustainabilityPercentage = BigDecimal.valueOf(response.getSustainableChoicesCount())
                    .divide(BigDecimal.valueOf(response.getTotalOrders()), 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            response.setSustainabilityPercentage(sustainabilityPercentage);
        }

        // Agregar datos adicionales si se solicitaron
        if (request.isIncludeProductBreakdown()) {
            response.setTopSustainableProducts(
                    calculationService.getTopSustainableProducts(orders)
            );
        }

        if (request.isIncludeCategoryAnalysis()) {
            response.setCategoryImpactBreakdown(
                    calculationService.getCategoryImpactBreakdown(orders)
            );
        }

        if (request.isIncludeMonthlyTrend()) {
            response.setMonthlyTrend(
                    calculationService.getMonthlyTrend(orders,
                            request.getStartDate() != null ? request.getStartDate().toLocalDate() : null,
                            request.getEndDate() != null ? request.getEndDate().toLocalDate() : null)
            );
        }

        // Calcular achievements
        response.setAchievements(
                calculationService.calculateEcoAchievements(response)
        );
    }
}