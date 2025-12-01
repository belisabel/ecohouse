package com.EcoHouse.product.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnvironmentalDataDTO {

    private Long id;
    private BigDecimal carbonFootprint;
    private String material;
    private String countryOfOrigin;
    private BigDecimal energyConsumption;
    private BigDecimal recyclablePercentage;
    private String notes;
    private Date createdAt;
}
