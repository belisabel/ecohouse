package com.EcoHouse.product.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationDTO {
    private Long id;
    private String name;
    private String description;
    private String organization;
    private String certificationUrl;
}
