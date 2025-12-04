package com.EcoHouse.product.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO de respuesta para EnvironmentalData - incluye id y timestamps autogenerados
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnvironmentalDataResponse {
    private Long id;
    private BigDecimal carbonFootprint;
    private String material;
    private String countryOfOrigin;
    private BigDecimal energyConsumption;
    private BigDecimal recyclablePercentage;
    private String notes;
    private Date createdAt;
}
