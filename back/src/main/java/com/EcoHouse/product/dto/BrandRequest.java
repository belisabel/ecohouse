package com.EcoHouse.product.dto;

import lombok.*;

/**
 * DTO de request para Brand - sin id (para POST)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandRequest {
    private String name;
    private String description;
    private String websiteUrl;
    private String country;
}

