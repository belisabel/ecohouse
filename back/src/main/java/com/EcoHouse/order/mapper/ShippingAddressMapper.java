package com.EcoHouse.order.mapper;

import com.EcoHouse.order.dto.ShippingAddressDTO;
import com.EcoHouse.order.model.ShippingAddress;

public class ShippingAddressMapper {

    // Entity → DTO
    public static ShippingAddressDTO toDTO(ShippingAddress entity) {
        if (entity == null) return null;

        ShippingAddressDTO dto = new ShippingAddressDTO();
        dto.setStreet(entity.getStreet());
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setZipCode(entity.getZipCode());
        dto.setCountry(entity.getCountry());

        return dto;
    }

    // DTO → Entity
    public static ShippingAddress toEntity(ShippingAddressDTO dto) {
        if (dto == null) return null;

        ShippingAddress entity = new ShippingAddress();
        entity.setStreet(dto.getStreet());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setZipCode(dto.getZipCode());
        entity.setCountry(dto.getCountry());

        return entity;
    }
}
