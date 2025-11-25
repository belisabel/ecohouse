package com.EcoHouse.category.repository;

import com.EcoHouse.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Categorías raíz (sin padre)
    List<Category> findByParentCategoryIsNull();

    // Subcategorías de una categoría específica
    List<Category> findByParentCategoryId(Long parentId);

    // Búsqueda por nombre
    List<Category> findByNameContainingIgnoreCase(String name);

}

