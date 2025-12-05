package com.EcoHouse.product.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * DTO de respuesta para Product - incluye id y timestamps autogenerados
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private List<String> additionalImages;
    private Integer stock;
    private Long brandId;
    private String brandName;
    private Long categoryId;
    private String categoryName;
    private EnvironmentalDataResponse environmentalData;
    private List<Long> certificationIds;

    private Boolean isActive;
    private Date createdAt;
}
