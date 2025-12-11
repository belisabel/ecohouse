package com.EcoHouse.product.repository;

import com.EcoHouse.product.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Buscar productos activos
    List<Product> findByIsActiveTrue();

    // Buscar por categoría
    List<Product> findByCategoryId(Long categoryId);

    // Buscar por marca
    List<Product> findByBrandId(Long brandId);

    // Buscar por nombre (similar a un search)
    List<Product> findByNameContainingIgnoreCase(String name);

    // ========== QUERIES CON JOIN FETCH PARA EVITAR LazyInitializationException ==========

    /**
     * Obtener todos los productos con sus relaciones cargadas (Brand, Category, EnvironmentalData)
     * Esto evita LazyInitializationException cuando se accede a estas relaciones fuera de transacción
     */
    @Query("SELECT DISTINCT p FROM Product p " +
           "LEFT JOIN FETCH p.brand " +
           "LEFT JOIN FETCH p.category " +
           "LEFT JOIN FETCH p.environmentalData " +
           "LEFT JOIN FETCH p.certifications")
    List<Product> findAllWithRelations();

    /**
     * Obtener un producto por ID con todas sus relaciones cargadas
     */
    @Query("SELECT p FROM Product p " +
           "LEFT JOIN FETCH p.brand " +
           "LEFT JOIN FETCH p.category " +
           "LEFT JOIN FETCH p.environmentalData " +
           "LEFT JOIN FETCH p.certifications " +
           "WHERE p.id = :id")
    Optional<Product> findByIdWithRelations(Long id);


    /*** todos los productos activos ordenados por menor huella de carbono*/
    @Query("""
    SELECT p FROM Product p
    LEFT JOIN FETCH p.environmentalData ed
    WHERE p.isActive = true AND ed.carbonFootprint IS NOT NULL
    ORDER BY ed.carbonFootprint ASC
""")
    List<Product> findAllOrderByCarbonFootprintAsc();


    /*** Traer SOLO los 5 productos con menor huella*/

    @Query("""
    SELECT p FROM Product p
    LEFT JOIN p.environmentalData ed
    WHERE p.isActive = true AND ed.carbonFootprint IS NOT NULL
    ORDER BY ed.carbonFootprint ASC
""")
    List<Product> findTop5OrderByCarbonFootprintAsc(Pageable pageable);

}
