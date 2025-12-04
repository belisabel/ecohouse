package com.EcoHouse.product.mapper;

import com.EcoHouse.product.dto.BrandDTO;
import com.EcoHouse.product.model.Brand;

public class BrandMapper {

    public static BrandDTO toDTO(Brand brand) {
        if (brand == null) return null;

        return BrandDTO.builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .websiteUrl(brand.getImageUrl())
                .country(brand.getCountry())
                .build();
    }

    public static Brand toEntity(BrandDTO dto) {
        if (dto == null) return null;

        Brand brand = new Brand();
        brand.setId(dto.getId());
        brand.setName(dto.getName());
        brand.setDescription(dto.getDescription());
        brand.setImageUrl(dto.getWebsiteUrl());
        brand.setCountry(dto.getCountry());

        return brand;
    }
}
