package com.EcoHouse.product.dto;

import lombok.*;

/**
 * DTO de respuesta para Brand - incluye id autogenerado
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponse {
    private Long id;
    private String name;
    private String description;
    private String websiteUrl;
    private String country;
}
