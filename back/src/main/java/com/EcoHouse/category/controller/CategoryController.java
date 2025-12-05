package com.EcoHouse.category.controller;

import com.EcoHouse.category.dto.CategoryResponse;
import com.EcoHouse.category.dto.CategoryRequest;
import com.EcoHouse.category.mapper.CategoryMapper;
import com.EcoHouse.category.model.Category;
import com.EcoHouse.category.services.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {
        Category parent = null;

        if (request.getParentCategoryId() != null) {
            parent = categoryService.getCategoryById(request.getParentCategoryId());
        }

        Category category = CategoryMapper.toEntity(request, parent);
        Category saved = categoryService.createCategory(category);

        return ResponseEntity.ok(CategoryMapper.toDTO(saved));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> list = categoryService.getAllCategories()
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(CategoryMapper.toDTO(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequest request) {

        Category parent = null;

        if (request.getParentCategoryId() != null) {
            parent = categoryService.getCategoryById(request.getParentCategoryId());
        }

        Category updatedEntity = categoryService.updateCategory(id,
                CategoryMapper.toEntity(request, parent));

        return ResponseEntity.ok(CategoryMapper.toDTO(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/root")
    public ResponseEntity<List<CategoryResponse>> getRootCategories() {
        List<CategoryResponse> list = categoryService.getRootCategories()
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    @GetMapping("/sub/{parentId}")
    public ResponseEntity<List<CategoryResponse>> getSubCategories(@PathVariable Long parentId) {
        List<CategoryResponse> list = categoryService.getSubCategories(parentId)
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }
}
