package com.EcoHouse.impactReport.service;

import com.EcoHouse.impactReport.dto.OrderDataDto;
import com.EcoHouse.impactReport.dtoResponse.ImpactReportResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Servicio para realizar cálculos de impacto ambiental
 */
@Service
@Slf4j
public class ImpactCalculationService {

    /**
     * Calcula las métricas totales de impacto de una lista de órdenes
     */
    public ImpactMetrics calculateImpactMetrics(List<OrderDataDto> orders) {
        BigDecimal totalCO2Saved = BigDecimal.ZERO;
        BigDecimal totalCO2Footprint = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        Integer totalEcoPoints = 0;
        Integer sustainableChoicesCount = 0;

        for (OrderDataDto order : orders) {
            totalCO2Saved = totalCO2Saved.add(order.getCo2Saved());
            totalCO2Footprint = totalCO2Footprint.add(order.getTotalCarbonFootprint());
            totalAmount = totalAmount.add(order.getTotalAmount());
            totalEcoPoints += order.getEcoPointsEarned();

            // Contar elecciones sustentables
            if (order.getItems() != null) {
                sustainableChoicesCount += (int) order.getItems().stream()
                        .filter(OrderDataDto.OrderItemDataDto::getIsSustainable)
                        .count();
            }
        }

        return ImpactMetrics.builder()
                .totalCO2Saved(totalCO2Saved)
                .totalCO2Footprint(totalCO2Footprint)
                .totalAmount(totalAmount)
                .totalEcoPoints(totalEcoPoints)
                .sustainableChoicesCount(sustainableChoicesCount.intValue())
                .totalOrders(orders.size())
                .build();
    }

    /**
     * Calcula un score de eficiencia ecológica (0-100)
     */
    public BigDecimal calculateEcoEfficiencyScore(ImpactMetrics metrics) {
        // Si no hay huella de carbono (sin compras), retornar 0
        if (metrics.getTotalCO2Footprint().compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        // Score basado en: CO2 ahorrado / CO2 total * 100
        BigDecimal ratio = metrics.getTotalCO2Saved()
                .divide(metrics.getTotalCO2Footprint(), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        // Limitar entre 0 y 100
        if (ratio.compareTo(BigDecimal.valueOf(100)) > 0) {
            return BigDecimal.valueOf(100);
        }
        if (ratio.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }

        return ratio.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Determina el nivel de impacto basado en el score
     */
    public String determineImpactLevel(BigDecimal ecoScore) {
        // Caso especial: sin compras (score = 0)
        if (ecoScore.compareTo(BigDecimal.ZERO) == 0) {
            return "SIN DATOS";
        }

        if (ecoScore.compareTo(BigDecimal.valueOf(80)) >= 0) {
            return "EXCELENTE";
        } else if (ecoScore.compareTo(BigDecimal.valueOf(60)) >= 0) {
            return "BUENO";
        } else if (ecoScore.compareTo(BigDecimal.valueOf(40)) >= 0) {
            return "MODERADO";
        } else {
            return "BAJO";
        }
    }

    /**
     * Obtiene los productos más sustentables
     */
    public List<com.EcoHouse.impactReport.dtoResponse.ImpactReportResponseDto.SustainableProductDto> getTopSustainableProducts(List<OrderDataDto> orders) {
        Map<Long, ProductImpact> productMap = new HashMap<>();

        for (OrderDataDto order : orders) {
            if (order.getItems() != null) {
                for (OrderDataDto.OrderItemDataDto item : order.getItems()) {
                    if (Boolean.TRUE.equals(item.getIsSustainable())) {
                        productMap.computeIfAbsent(item.getProductId(), k ->
                                new ProductImpact(item.getProductId(), item.getProductName()))
                                .addPurchase(item.getQuantity(), item.getCo2Saved(), item.getSubtotal());
                    }
                }
            }
        }

        return productMap.values().stream()
                .sorted(Comparator.comparing(ProductImpact::getTotalCO2Saved).reversed())
                .limit(5)
                .map(p -> com.EcoHouse.impactReport.dtoResponse.ImpactReportResponseDto.SustainableProductDto.builder()
                        .productId(p.getProductId())
                        .productName(p.getProductName())
                        .timesPurchased(p.getTimesPurchased())
                        .co2Saved(p.getTotalCO2Saved())
                        .totalSpent(p.getTotalSpent())
                        .ecoImpactLevel(determineProductImpactLevel(p.getTotalCO2Saved()))
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Obtiene el desglose por categoría
     */
    public Map<String, BigDecimal> getCategoryImpactBreakdown(List<OrderDataDto> orders) {
        Map<String, CategoryImpact> categoryMap = new HashMap<>();

        for (OrderDataDto order : orders) {
            if (order.getItems() != null) {
                for (OrderDataDto.OrderItemDataDto item : order.getItems()) {
                    categoryMap.computeIfAbsent(item.getCategoryName(), CategoryImpact::new)
                            .addItem(item.getSubtotal(), item.getCo2Saved(), item.getQuantity());
                }
            }
        }

        return categoryMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().getCo2Saved()
                ));
    }

    /**
     * Obtiene la tendencia mensual
     */
    public List<ImpactReportResponseDto.MonthlyImpactDto> getMonthlyTrend(
            List<OrderDataDto> orders,
            LocalDate startDate,
            LocalDate endDate
    ) {
        Map<YearMonth, MonthlyData> monthlyMap = new TreeMap<>();

        for (OrderDataDto order : orders) {
            if (order.getOrderDate() != null) {
                YearMonth yearMonth = YearMonth.from(order.getOrderDate());
                monthlyMap.computeIfAbsent(yearMonth, MonthlyData::new)
                        .addOrder(order.getTotalAmount(), order.getCo2Saved());
            }
        }

        return monthlyMap.entrySet().stream()
                .map(e -> ImpactReportResponseDto.MonthlyImpactDto.builder()
                        .month(e.getKey().toString())
                        .ordersCount(e.getValue().getOrderCount())
                        .amountSpent(e.getValue().getTotalSpent())
                        .co2Saved(e.getValue().getCo2Saved())
                        .co2Footprint(BigDecimal.ZERO) // Se puede calcular si es necesario
                        .ecoPoints(0) // Se puede calcular si es necesario
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Calcula logros ecológicos
     */
    public ImpactReportResponseDto.EcoAchievementsDto calculateEcoAchievements(ImpactReportResponseDto response) {
        List<String> badges = new ArrayList<>();

        if (response.getTotalCO2Saved() != null) {
            if (response.getTotalCO2Saved().compareTo(BigDecimal.valueOf(50)) >= 0) {
                badges.add("🌱 Eco Héroe - Ahorraste más de 50 kg de CO2");
            } else if (response.getTotalCO2Saved().compareTo(BigDecimal.valueOf(20)) >= 0) {
                badges.add("🌿 Amigo del Planeta - Ahorraste más de 20 kg de CO2");
            } else if (response.getTotalCO2Saved().compareTo(BigDecimal.valueOf(10)) >= 0) {
                badges.add("🍃 Comprador Consciente - Ahorraste más de 10 kg de CO2");
            }
        }

        if (response.getSustainableChoicesCount() != null && response.getSustainableChoicesCount() >= 10) {
            badges.add("♻️ Campeón de la Sostenibilidad - 10+ productos ecológicos");
        }

        if (response.getEcoEfficiencyScore() != null &&
                response.getEcoEfficiencyScore().compareTo(BigDecimal.valueOf(80)) >= 0) {
            badges.add("⭐ Excelencia Verde - Score de eficiencia >80%");
        }

        // Determinar el siguiente hito
        String nextMilestone = "Ninguno";
        BigDecimal progressToNextLevel = BigDecimal.ZERO;

        if (response.getTotalCO2Saved() != null) {
            if (response.getTotalCO2Saved().compareTo(BigDecimal.valueOf(50)) < 0) {
                nextMilestone = "Eco Héroe (50 kg CO2)";
                progressToNextLevel = response.getTotalCO2Saved()
                        .divide(BigDecimal.valueOf(50), 2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
            } else if (response.getTotalCO2Saved().compareTo(BigDecimal.valueOf(100)) < 0) {
                nextMilestone = "Maestro Verde (100 kg CO2)";
                progressToNextLevel = response.getTotalCO2Saved()
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
            }
        }

        return ImpactReportResponseDto.EcoAchievementsDto.builder()
                .badges(badges)
                .sustainabilityRank(calculateRank(response))
                .nextMilestone(nextMilestone)
                .progressToNextLevel(progressToNextLevel)
                .build();
    }

    /**
     * Calcula el ranking de sostenibilidad (simplificado)
     */
    private Integer calculateRank(ImpactReportResponseDto response) {
        // Ranking basado en eco efficiency score
        if (response.getEcoEfficiencyScore() != null) {
            if (response.getEcoEfficiencyScore().compareTo(BigDecimal.valueOf(80)) >= 0) {
                return 1; // Top tier
            } else if (response.getEcoEfficiencyScore().compareTo(BigDecimal.valueOf(60)) >= 0) {
                return 2; // High tier
            } else if (response.getEcoEfficiencyScore().compareTo(BigDecimal.valueOf(40)) >= 0) {
                return 3; // Medium tier
            } else {
                return 4; // Low tier
            }
        }
        return 5; // Sin ranking
    }

    // Clases internas para cálculos
    @lombok.Data
    private static class ProductImpact {
        private Long productId;
        private String productName;
        private int timesPurchased = 0;
        private BigDecimal totalCO2Saved = BigDecimal.ZERO;
        private BigDecimal totalSpent = BigDecimal.ZERO;

        public ProductImpact(Long productId, String productName) {
            this.productId = productId;
            this.productName = productName;
        }

        public void addPurchase(int quantity, BigDecimal co2Saved, BigDecimal amount) {
            this.timesPurchased += quantity;
            this.totalCO2Saved = this.totalCO2Saved.add(co2Saved);
            this.totalSpent = this.totalSpent.add(amount);
        }
    }

    /**
     * Determina el nivel de impacto de un producto
     */
    private String determineProductImpactLevel(BigDecimal co2Saved) {
        if (co2Saved.compareTo(BigDecimal.valueOf(10)) >= 0) {
            return "ALTO";
        } else if (co2Saved.compareTo(BigDecimal.valueOf(5)) >= 0) {
            return "MEDIO";
        } else {
            return "BAJO";
        }
    }

    @lombok.Data
    private static class CategoryImpact {
        private String categoryName;
        private BigDecimal totalSpent = BigDecimal.ZERO;
        private BigDecimal co2Saved = BigDecimal.ZERO;
        private int itemCount = 0;

        public CategoryImpact(String categoryName) {
            this.categoryName = categoryName;
        }

        public void addItem(BigDecimal amount, BigDecimal co2, int quantity) {
            this.totalSpent = this.totalSpent.add(amount);
            this.co2Saved = this.co2Saved.add(co2);
            this.itemCount += quantity;
        }
    }

    @lombok.Data
    private static class MonthlyData {
        private YearMonth month;
        private int orderCount = 0;
        private BigDecimal totalSpent = BigDecimal.ZERO;
        private BigDecimal co2Saved = BigDecimal.ZERO;

        public MonthlyData(YearMonth month) {
            this.month = month;
        }

        public void addOrder(BigDecimal amount, BigDecimal co2) {
            this.orderCount++;
            this.totalSpent = this.totalSpent.add(amount);
            this.co2Saved = this.co2Saved.add(co2);
        }
    }

    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class ImpactMetrics {
        private BigDecimal totalCO2Saved;
        private BigDecimal totalCO2Footprint;
        private BigDecimal totalAmount;
        private Integer totalEcoPoints;
        private Integer sustainableChoicesCount;
        private Integer totalOrders;
    }
}

