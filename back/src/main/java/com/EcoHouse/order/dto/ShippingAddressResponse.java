package com.EcoHouse.order.dto;

import lombok.*;

/**
 * DTO de respuesta para ShippingAddress
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingAddressResponse {
    private String street;
    private String number;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
