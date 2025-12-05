package com.EcoHouse.order.dto;

import lombok.*;

/**
 * DTO de request para ShippingAddress - para POST (no tiene id propio, es embedded)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingAddressRequest {
    private String street;
    private String number;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}

