package com.EcoHouse.category.mapper;

import com.EcoHouse.category.dto.CategoryDTO;
import com.EcoHouse.category.dto.CategoryRequest;
import com.EcoHouse.category.model.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    public static CategoryDTO toDTO(Category category) {
        if (category == null) return null;

        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setIconUrl(category.getIconUrl());

        if (category.getParentCategory() != null) {
            dto.setParentCategoryId(category.getParentCategory().getId());
        }

        // Subcategorías (recursivo)
        if (category.getSubCategories() != null) {
            dto.setSubCategories(
                    category.getSubCategories()
                            .stream()
                            .map(CategoryMapper::toDTO)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public static Category toEntity(CategoryRequest request, Category parentCategory) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setIconUrl(request.getIconUrl());
        category.setParentCategory(parentCategory);

        return category;
    }
}
