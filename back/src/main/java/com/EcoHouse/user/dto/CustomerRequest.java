package com.EcoHouse.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de request para Customer - sin id ni timestamps (para POST/PUT)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String shippingAddress;
    private String billingAddress;
    private String phone;
    private Double carbonFootprint;
}

