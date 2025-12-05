package com.EcoHouse.category.mapper;

import com.EcoHouse.category.dto.CategoryResponse;
import com.EcoHouse.category.dto.CategoryRequest;
import com.EcoHouse.category.model.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    public static CategoryResponse toDTO(Category category) {
        if (category == null) return null;

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .iconUrl(category.getIconUrl())
                .parentCategoryId(category.getParentCategory() != null ? category.getParentCategory().getId() : null)
                .subCategories(category.getSubCategories() != null ?
                    category.getSubCategories().stream()
                        .map(CategoryMapper::toDTO)
                        .collect(Collectors.toList()) : null)
                .build();
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
