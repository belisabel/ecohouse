package com.EcoHouse.category.services;

import com.EcoHouse.category.model.Category;

import java.util.List;

public interface ICategoryService {

    // Crear una nueva categoría
    Category createCategory(Category category);

    // Actualizar una categoría existente
    Category updateCategory(Long id, Category category);

    // Obtener una categoría por su ID
    Category getCategoryById(Long id);

    // Obtener todas las categorías
    List<Category> getAllCategories();

    // Obtener categorías raíz (sin padre)
    List<Category> getRootCategories();

    // Obtener subcategorías de una categoría específica
    List<Category> getSubCategories(Long parentId);

    //Eliminar una categoría por su ID
    void deleteCategory(Long id);

}
