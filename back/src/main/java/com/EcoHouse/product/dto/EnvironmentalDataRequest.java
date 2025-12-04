package com.EcoHouse.product.dto;

import lombok.*;
import java.math.BigDecimal;

/**
 * DTO de request para EnvironmentalData - sin id ni timestamps (para POST)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnvironmentalDataRequest {
    private BigDecimal carbonFootprint;
    private String material;
    private String countryOfOrigin;
    private BigDecimal energyConsumption;
    private BigDecimal recyclablePercentage;
    private String notes;
}

