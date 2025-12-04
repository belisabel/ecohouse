package com.EcoHouse.product.mapper;

import com.EcoHouse.product.dto.EnvironmentalDataResponse;
import com.EcoHouse.product.dto.EnvironmentalDataRequest;
import com.EcoHouse.product.model.EnvironmentalData;

public class EnvironmentalDataMapper {

    public static EnvironmentalDataResponse toDTO(EnvironmentalData data) {
        if (data == null) return null;

        return EnvironmentalDataResponse.builder()
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

    public static EnvironmentalData toEntity(EnvironmentalDataRequest request) {
        if (request == null) return null;

        EnvironmentalData data = new EnvironmentalData();
        data.setCarbonFootprint(request.getCarbonFootprint());
        data.setMaterial(request.getMaterial());
        data.setCountryOfOrigin(request.getCountryOfOrigin());
        data.setEnergyConsumption(request.getEnergyConsumption());
        data.setRecyclablePercentage(request.getRecyclablePercentage());
        data.setNotes(request.getNotes());
        return data;
    }
}
