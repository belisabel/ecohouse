package com.EcoHouse.product.services;

import com.EcoHouse.product.model.Brand;
import com.EcoHouse.product.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements IBrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Brand createBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand updateBrand(Long id, Brand brand) {
        Brand existing = getBrandById(id);

        existing.setName(brand.getName());
        existing.setDescription(brand.getDescription());
        existing.setImageUrl(brand.getImageUrl());
        existing.setCountry(brand.getCountry());

        return brandRepository.save(existing);
    }

    @Override
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

    @Override
    public Brand getBrandById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
    }

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }
}
