package com.EcoHouse.order.service;

import com.EcoHouse.order.model.Order;
import com.EcoHouse.order.model.OrderItem;
import com.EcoHouse.order.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Servicio para calcular EcoPoints basados en el impacto ambiental real de las órdenes
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EcoPointsCalculationService {

    /**
     * Calcula los EcoPoints de una orden basándose en múltiples factores
     *
     * Fórmula: (Base + CO2 + Productos + Gasto) × Multiplicador
     *
     * Donde:
     * - Base: 10 puntos por orden
     * - CO2: 1 punto por kg de CO2 ahorrado
     * - Productos: 5 puntos por producto sostenible
     * - Gasto: 1 punto por cada $10 USD
     * - Multiplicador: 0.8x - 1.5x según impact level
     *
     * @param order La orden a evaluar
     * @return Número de EcoPoints ganados
     */
    public Integer calculateEcoPoints(Order order) {
        if (order == null || order.getStatus() != OrderStatus.DELIVERED) {
            log.debug("Orden no válida para EcoPoints: orden={}, estado={}",
                     order != null ? order.getId() : "null",
                     order != null ? order.getStatus() : "null");
            return 0;
        }

        int points = 0;

        // 1. Puntos base por orden completada
        points += 10;
        log.debug("Base points: 10");

        // 2. Puntos por CO2 ahorrado (1 punto por kg)
        BigDecimal co2Saved = calculateCO2Saved(order);
        int co2Points = co2Saved.intValue();
        points += co2Points;
        log.debug("CO2 points: {} (CO2 saved: {} kg)", co2Points, co2Saved);

        // 3. Bonus por productos sostenibles (5 puntos c/u)
        long sustainableCount = countSustainableProducts(order);
        int productPoints = (int) sustainableCount * 5;
        points += productPoints;
        log.debug("Product points: {} ({} sustainable products)", productPoints, sustainableCount);

        // 4. Puntos por gasto (1 punto por cada $10 USD)
        int spendingPoints = order.getTotalAmount()
                .divide(BigDecimal.TEN, RoundingMode.DOWN)
                .intValue();
        points += spendingPoints;
        log.debug("Spending points: {} (${} total)", spendingPoints, order.getTotalAmount());

        // 5. Aplicar multiplicador según impact level
        String impactLevel = determineImpactLevel(order, co2Saved);
        int finalPoints = applyMultiplier(points, impactLevel);

        log.info("EcoPoints calculados para orden {}: {} puntos base × {} ({}) = {} puntos finales",
                order.getId(), points, getMultiplierValue(impactLevel), impactLevel, finalPoints);

        return finalPoints;
    }

    /**
     * Calcula el CO2 ahorrado de una orden
     * Se asume que los productos ecológicos ahorran 30% de CO2 vs convencionales
     */
    private BigDecimal calculateCO2Saved(Order order) {
        if (order.getItems() == null || order.getItems().isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalFootprint = order.getItems().stream()
                .filter(item -> item.getProduct() != null)
                .filter(item -> item.getProduct().getEnvironmentalData() != null)
                .map(item -> {
                    BigDecimal itemFootprint = item.getProduct()
                            .getEnvironmentalData()
                            .getCarbonFootprint();
                    return itemFootprint.multiply(new BigDecimal(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Los productos ecológicos ahorran aproximadamente 30% de CO2
        return totalFootprint.multiply(new BigDecimal("0.30"));
    }

    /**
     * Cuenta cuántos productos sostenibles (con datos ambientales) tiene la orden
     */
    private long countSustainableProducts(Order order) {
        if (order.getItems() == null || order.getItems().isEmpty()) {
            return 0;
        }

        return order.getItems().stream()
                .filter(item -> item.getProduct() != null)
                .filter(item -> item.getProduct().getEnvironmentalData() != null)
                .count();
    }

    /**
     * Determina el nivel de impacto de una orden basándose en la eficiencia de CO2
     */
    private String determineImpactLevel(Order order, BigDecimal co2Saved) {
        BigDecimal totalFootprint = calculateTotalFootprint(order);

        if (totalFootprint.compareTo(BigDecimal.ZERO) == 0) {
            return "SIN_DATOS";
        }

        // Calcular ratio de eficiencia (CO2 ahorrado / CO2 total) × 100
        BigDecimal ratio = co2Saved.divide(totalFootprint, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"));

        if (ratio.compareTo(new BigDecimal("80")) >= 0) {
            return "EXCELENTE";
        } else if (ratio.compareTo(new BigDecimal("60")) >= 0) {
            return "BUENO";
        } else if (ratio.compareTo(new BigDecimal("40")) >= 0) {
            return "MODERADO";
        } else {
            return "BAJO";
        }
    }

    /**
     * Calcula la huella de carbono total de una orden
     */
    private BigDecimal calculateTotalFootprint(Order order) {
        if (order.getItems() == null || order.getItems().isEmpty()) {
            return BigDecimal.ZERO;
        }

        return order.getItems().stream()
                .filter(item -> item.getProduct() != null)
                .filter(item -> item.getProduct().getEnvironmentalData() != null)
                .map(item -> {
                    BigDecimal itemFootprint = item.getProduct()
                            .getEnvironmentalData()
                            .getCarbonFootprint();
                    return itemFootprint.multiply(new BigDecimal(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Aplica el multiplicador según el nivel de impacto
     */
    private Integer applyMultiplier(Integer points, String impactLevel) {
        double multiplier = getMultiplierValue(impactLevel);
        return (int) (points * multiplier);
    }

    /**
     * Obtiene el valor del multiplicador según el nivel de impacto
     */
    private double getMultiplierValue(String impactLevel) {
        return switch (impactLevel) {
            case "EXCELENTE" -> 1.5;
            case "BUENO" -> 1.2;
            case "MODERADO" -> 1.0;
            case "BAJO" -> 0.8;
            default -> 0.5; // SIN_DATOS
        };
    }

    /**
     * Determina el nivel de EcoHero basado en puntos acumulados
     */
    public String determineEcoLevel(Integer totalPoints) {
        if (totalPoints == null || totalPoints < 0) {
            return "ECO_NOVATO";
        }

        if (totalPoints >= 5000) {
            return "ECO_LEGEND";
        } else if (totalPoints >= 1000) {
            return "ECO_CHAMPION";
        } else if (totalPoints >= 500) {
            return "ECO_HERO";
        } else if (totalPoints >= 100) {
            return "ECO_CONSCIENTE";
        } else {
            return "ECO_NOVATO";
        }
    }

    /**
     * Obtiene el nombre legible del nivel de EcoHero
     */
    public String getEcoLevelName(String level) {
        return switch (level) {
            case "ECO_LEGEND" -> "👑 Eco Legend";
            case "ECO_CHAMPION" -> "🏆 Eco Champion";
            case "ECO_HERO" -> "🌳 Eco Héroe";
            case "ECO_CONSCIENTE" -> "🌿 Eco Consciente";
            default -> "🌱 Eco Novato";
        };
    }

    /**
     * Obtiene el badge correspondiente al nivel
     */
    public String getEcoLevelBadge(String level) {
        return switch (level) {
            case "ECO_LEGEND" -> "👑";
            case "ECO_CHAMPION" -> "🏆";
            case "ECO_HERO" -> "🌳";
            case "ECO_CONSCIENTE" -> "🌿";
            default -> "🌱";
        };
    }
}

