package com.EcoHouse.product.mapper;

import com.EcoHouse.product.model.Product;
import com.EcoHouse.category.model.Category;
import com.EcoHouse.product.dto.ProductResponse;
import com.EcoHouse.product.dto.ProductRequest;
import com.EcoHouse.product.model.Brand;
import com.EcoHouse.product.model.EnvironmentalData;
import com.EcoHouse.product.model.Certification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public ProductResponse toDTO(Product product) {
        if (product == null)
            return null;

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice() != null ? product.getPrice().doubleValue() : null)
                .imageUrl(product.getImageUrl())
                .stock(product.getStock())
                .isActive(product.getIsActive())
                .brandName(product.getBrand() != null ? product.getBrand().getName() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .certificationNames(product.getCertifications() != null ? product.getCertifications().stream().map(Certification::getName).collect(Collectors.toList()) : null)
                .environmentalData(product.getEnvironmentalData() != null ? EnvironmentalDataMapper.toDTO(product.getEnvironmentalData()) : null)
                .build();
    }

    public Product toEntity(ProductRequest request) {
        if (request == null)
            return null;

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());
        product.setStock(request.getStock());

        // Brand, Category, EnvironmentalData y Certifications se setean en el service
        return product;
    }
}
