package com.EcoHouse.product.mapper;

import com.EcoHouse.product.model.Product;
import com.EcoHouse.category.model.Category;
import com.EcoHouse.product.dto.ProductDTO;
import com.EcoHouse.product.model.Brand;
import com.EcoHouse.product.model.EnvironmentalData;
import com.EcoHouse.product.model.Certification;

import java.util.List;
import java.util.stream.Collectors;
public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        if (product == null)
            return null;

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .additionalImages(product.getAdditionalImages())
                .stock(product.getStock())
                .brandId(product.getBrand() != null ? product.getBrand().getId() : null)
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .environmentalDataId(
                        product.getEnvironmentalData() != null ? product.getEnvironmentalData().getId() : null)
                .certificationIds(product.getCertifications() != null
                        ? product.getCertifications().stream().map(Certification::getId).collect(Collectors.toList())
                        : null)
                .isActive(product.getIsActive())
                .createdAt(product.getCreatedAt())
                .build();
    }

    public static Product toEntity(ProductDTO dto) {
        if (dto == null)
            return null;

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageUrl());
        product.setAdditionalImages(dto.getAdditionalImages());
        product.setStock(dto.getStock());
        product.setIsActive(dto.getIsActive());
        product.setCreatedAt(dto.getCreatedAt());

        // Estos se cargan luego en el service con findById
        if (dto.getBrandId() != null) {
            Brand brand = new Brand();
            brand.setId(dto.getBrandId());
            product.setBrand(brand);
        }

        if (dto.getCategoryId() != null) {
            Category category = new Category();
            category.setId(dto.getCategoryId());
            product.setCategory(category);
        }

        if (dto.getEnvironmentalDataId() != null) {
            EnvironmentalData data = new EnvironmentalData();
            data.setId(dto.getEnvironmentalDataId());
            product.setEnvironmentalData(data);
        }
        if (dto.getCertificationIds() != null) {
            List<Certification> certs = dto.getCertificationIds().stream().map(id -> {
                Certification c = new Certification();
                c.setId(id);
                return c;
            }).collect(Collectors.toList());
            product.setCertifications(certs);
        }

        // Las certificaciones se agregan en el servicio también
        return product;
    }
}
