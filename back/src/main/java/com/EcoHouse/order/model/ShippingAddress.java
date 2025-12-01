package com.EcoHouse.order.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddress {

    // Calle y número
    private String street;
    private String number;

    // Ciudad
    private String city;

    // Provincia o estado
    private String state;

    // País
    private String country;

    // Código postal
    private String zipCode;
}
