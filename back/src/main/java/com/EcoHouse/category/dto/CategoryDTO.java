package com.EcoHouse.category.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private String iconUrl;

    private Long parentCategoryId;

    // Para mostrar subcategorías en el árbol
    private List<CategoryDTO> subCategories;
}
