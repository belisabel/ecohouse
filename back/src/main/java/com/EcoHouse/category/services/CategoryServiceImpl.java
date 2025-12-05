package com.EcoHouse.category.services;

import com.EcoHouse.category.model.Category;
import com.EcoHouse.category.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, Category updatedData) {
        Category existing = getCategoryById(id);

        existing.setName(updatedData.getName());
        existing.setDescription(updatedData.getDescription());
        existing.setIconUrl(updatedData.getIconUrl());
        existing.setParentCategory(updatedData.getParentCategory());

        return categoryRepository.save(existing);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getRootCategories() {
        return categoryRepository.findByParentCategoryIsNull();
    }

    @Override
    public List<Category> getSubCategories(Long parentId) {
        return categoryRepository.findByParentCategoryId(parentId);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }
}


