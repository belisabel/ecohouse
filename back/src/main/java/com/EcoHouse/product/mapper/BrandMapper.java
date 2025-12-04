package com.EcoHouse.product.mapper;

import com.EcoHouse.product.dto.BrandResponse;
import com.EcoHouse.product.dto.BrandRequest;
import com.EcoHouse.product.model.Brand;

public class BrandMapper {

    public static BrandResponse toDTO(Brand brand) {
        if (brand == null) return null;

        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .websiteUrl(brand.getImageUrl())
                .country(brand.getCountry())
                .build();
    }

    public static Brand toEntity(BrandRequest request) {
        if (request == null) return null;

        Brand brand = new Brand();
        brand.setName(request.getName());
        brand.setDescription(request.getDescription());
        brand.setImageUrl(request.getWebsiteUrl());
        brand.setCountry(request.getCountry());

        return brand;
    }
}
