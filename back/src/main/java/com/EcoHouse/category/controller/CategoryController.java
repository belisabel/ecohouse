package com.EcoHouse.category.controller;

import com.EcoHouse.category.dto.CategoryDTO;
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

    // Crear categoría
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryRequest request) {
        Category parent = null;

        if (request.getParentCategoryId() != null) {
            parent = categoryService.getCategoryById(request.getParentCategoryId());
        }

        Category category = CategoryMapper.toEntity(request, parent);
        Category saved = categoryService.createCategory(category);

        return ResponseEntity.ok(CategoryMapper.toDTO(saved));
    }

    // Obtener todas las categorías
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> list = categoryService.getAllCategories()
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    // Obtener categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(CategoryMapper.toDTO(category));
    }

    // Actualizar categoría
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
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

    // Eliminar categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener categorías raíz
    @GetMapping("/root")
    public ResponseEntity<List<CategoryDTO>> getRootCategories() {
        List<CategoryDTO> list = categoryService.getRootCategories()
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    // Obtener subcategorías de un padre
    @GetMapping("/sub/{parentId}")
    public ResponseEntity<List<CategoryDTO>> getSubCategories(@PathVariable Long parentId) {
        List<CategoryDTO> list = categoryService.getSubCategories(parentId)
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }
}
