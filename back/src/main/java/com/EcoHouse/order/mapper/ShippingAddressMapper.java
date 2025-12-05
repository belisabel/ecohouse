package com.EcoHouse.order.mapper;

import com.EcoHouse.order.dto.ShippingAddressResponse;
import com.EcoHouse.order.dto.ShippingAddressRequest;
import com.EcoHouse.order.model.ShippingAddress;

public class ShippingAddressMapper {

    public static ShippingAddressResponse toDTO(ShippingAddress entity) {
        if (entity == null) return null;

        return ShippingAddressResponse.builder()
                .street(entity.getStreet())
                .number(entity.getNumber())
                .city(entity.getCity())
                .state(entity.getState())
                .zipCode(entity.getZipCode())
                .country(entity.getCountry())
                .build();
    }

    public static ShippingAddress toEntity(ShippingAddressRequest request) {
        if (request == null) return null;

        ShippingAddress entity = new ShippingAddress();
        entity.setStreet(request.getStreet());
        entity.setNumber(request.getNumber());
        entity.setCity(request.getCity());
        entity.setState(request.getState());
        entity.setZipCode(request.getZipCode());
        entity.setCountry(request.getCountry());

        return entity;
    }
}
