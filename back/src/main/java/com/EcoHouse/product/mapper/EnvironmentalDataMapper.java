package com.EcoHouse.product.mapper;

import com.EcoHouse.product.dto.EnvironmentalDataDTO;
import com.EcoHouse.product.model.EnvironmentalData;

public class EnvironmentalDataMapper {

    public static EnvironmentalDataDTO toDTO(EnvironmentalData data) {
        if (data == null) return null;

        return EnvironmentalDataDTO.builder()
                .id(data.getId())
                .carbonFootprint(data.getCarbonFootprint())
                .material(data.getMaterial())
                .countryOfOrigin(data.getCountryOfOrigin())
                .energyConsumption(data.getEnergyConsumption())
                .recyclablePercentage(data.getRecyclablePercentage())
                .notes(data.getNotes())
                .createdAt(data.getCreatedAt())
                .build();
    }

    public static EnvironmentalData toEntity(EnvironmentalDataDTO dto) {
        if (dto == null) return null;

        EnvironmentalData data = new EnvironmentalData();
        data.setId(dto.getId());
        data.setCarbonFootprint(dto.getCarbonFootprint());
        data.setMaterial(dto.getMaterial());
        data.setCountryOfOrigin(dto.getCountryOfOrigin());
        data.setEnergyConsumption(dto.getEnergyConsumption());
        data.setRecyclablePercentage(dto.getRecyclablePercentage());
        data.setNotes(dto.getNotes());
        data.setCreatedAt(dto.getCreatedAt());
        return data;
    }
}
