package com.EcoHouse.product.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Integer stock;
    private Boolean isActive;
    private String brandName;
    private String categoryName;
    private List<String> certificationNames;
}

