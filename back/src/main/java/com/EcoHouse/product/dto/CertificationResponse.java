package com.EcoHouse.product.dto;

import lombok.*;

/**
 * DTO de respuesta para Certification - incluye id autogenerado
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationResponse {
    private Long id;
    private String name;
    private String description;
    private String organization;
    private String certificationUrl;
}
