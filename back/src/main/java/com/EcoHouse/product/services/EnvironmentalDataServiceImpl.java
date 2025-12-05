package com.EcoHouse.product.services;

import com.EcoHouse.product.model.EnvironmentalData;
import com.EcoHouse.product.repository.EnvironmentalDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentalDataServiceImpl implements IEnvironmentalDataService {

    @Autowired
    private EnvironmentalDataRepository dataRepository;

    @Override
    public EnvironmentalData createEnvironmentalData(EnvironmentalData data) {
        return dataRepository.save(data);
    }

    @Override
    public EnvironmentalData updateEnvironmentalData(Long id, EnvironmentalData updated) {
        EnvironmentalData existing = getEnvironmentalDataById(id);

        existing.setCarbonFootprint(updated.getCarbonFootprint());
        existing.setEnergyConsumption(updated.getEnergyConsumption());
        existing.setRecyclablePercentage(updated.getRecyclablePercentage());
        existing.setMaterial(updated.getMaterial());

        return dataRepository.save(existing);
    }

    @Override
    public void deleteEnvironmentalData(Long id) {
        dataRepository.deleteById(id);
    }

    @Override
    public EnvironmentalData getEnvironmentalDataById(Long id) {
        return dataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Environmental data not found"));
    }
}
