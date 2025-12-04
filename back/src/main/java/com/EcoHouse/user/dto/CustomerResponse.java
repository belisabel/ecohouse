package com.EcoHouse.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO de respuesta para Customer - incluye id y timestamps autogenerados
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String shippingAddress;
    private String billingAddress;
    private String phone;
    private Double carbonFootprint;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

