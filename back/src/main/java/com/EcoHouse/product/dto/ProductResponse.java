package com.EcoHouse.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private List<String> additionalImages;
    private Integer stock;
    private String brandName;
    private String categoryName;
    private List<String> certificationNames;
    private Boolean isActive;
}
