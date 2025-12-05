package com.EcoHouse.category.dto;

import lombok.Data;

@Data
public class CategoryRequest {
    private String name;
    private String description;
    private String iconUrl;
    private Long parentCategoryId;
}
