package com.EcoHouse.product.services;

import com.EcoHouse.product.model.EnvironmentalData;

public interface IEnvironmentalDataService {

    // Crear 
    EnvironmentalData createEnvironmentalData(EnvironmentalData data);

    // Actualizar 
    EnvironmentalData updateEnvironmentalData(Long id, EnvironmentalData updated);

    void deleteEnvironmentalData(Long id);

    EnvironmentalData getEnvironmentalDataById(Long id);
}
