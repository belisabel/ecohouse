package com.EcoHouse.product.mapper;

import com.EcoHouse.product.dto.EnvironmentalDataResponse;
import com.EcoHouse.product.dto.EnvironmentalDataRequest;
import com.EcoHouse.product.model.EnvironmentalData;
import org.hibernate.Hibernate;

public class EnvironmentalDataMapper {

    public static EnvironmentalDataResponse toDTO(EnvironmentalData data) {
        if (data == null) return null;

        try {
            // Verificar si el proxy está inicializado antes de acceder a sus propiedades
            if (!Hibernate.isInitialized(data)) {
                return null;
            }

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
        } catch (jakarta.persistence.EntityNotFoundException e) {
            // Entidad referenciada no existe, retornar null
            return null;
        } catch (Exception e) {
            // Si hay cualquier otro error al acceder a los datos, retornar null
            return null;
        }
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
