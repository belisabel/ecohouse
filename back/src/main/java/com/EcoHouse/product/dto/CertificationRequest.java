package com.EcoHouse.product.dto;

import lombok.*;

/**
 * DTO de request para Certification - sin id (para POST)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationRequest {
    private String name;
    private String description;
    private String organization;
    private String certificationUrl;
}

