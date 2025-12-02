package com.EcoHouse.user.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerUpdateRequest {

    private String firstName;
    private String lastName;
    private String shippingAddress;
    private Double carbonFootprint;



}
