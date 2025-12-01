package com.EcoHouse.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingAddressDTO {
    private String street;
    private String number;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
