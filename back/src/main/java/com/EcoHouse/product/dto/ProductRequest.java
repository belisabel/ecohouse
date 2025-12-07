package com.EcoHouse.product.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO de request para Product - sin id ni timestamps (para POST)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Integer stock;
    private Long brandId;
    private Long categoryId;
    private EnvironmentalDataRequest environmentalData;
    private List<Long> certificationIds;
}

