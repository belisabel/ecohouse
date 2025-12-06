package com.EcoHouse.product.mapper;

import com.EcoHouse.product.model.Product;
import com.EcoHouse.category.model.Category;
import com.EcoHouse.product.dto.ProductResponse;
import com.EcoHouse.product.dto.ProductRequest;
import com.EcoHouse.product.model.Brand;
import com.EcoHouse.product.model.EnvironmentalData;
import com.EcoHouse.product.model.Certification;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductResponse toDTO(Product product) {
        if (product == null)
            return null;

        // ⚠️ SOLUCIÓN FINAL: NO acceder a NINGUNA relación lazy
        // Solo propiedades directas de Product (sin @ManyToOne, @OneToOne, @ManyToMany)
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .additionalImages(product.getAdditionalImages())
                .stock(product.getStock())
                .isActive(product.getIsActive())
                .createdAt(product.getCreatedAt())
                // NO acceder a Brand, Category, EnvironmentalData, Certifications (lazy)
                .brandId(null)
                .brandName(null)
                .categoryId(null)
                .categoryName(null)
                .environmentalData(null)
                .certificationIds(null)
                .build();
    }

    public static Product toEntity(ProductRequest request) {
        if (request == null)
            return null;

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());
        product.setAdditionalImages(request.getAdditionalImages());
        product.setStock(request.getStock());

        // Brand, Category, EnvironmentalData y Certifications se setean en el service
        return product;
    }
}
