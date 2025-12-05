package com.EcoHouse.product.services;

import com.EcoHouse.product.model.Product;

import java.util.List;

public interface IProductService {

    // Crear un nuevo producto
    Product createProduct(Product product, Long categoryId, Long brandId, List<Long> certificationIds);

    // Actualizar un producto existente
    Product updateProduct(Long id, Product product, Long categoryId, Long brandId, List<Long> certificationIds);

    // Obtener un producto por su ID
    Product getProductById(Long id);

    // Obtener todos los productos
    List<Product> getAllProducts();

    // Obtener productos activos
    List<Product> getActiveProducts();

    // Obtener productos por categoría
    List<Product> getProductsByCategory(Long categoryId);

    // Obtener productos por marca
    List<Product> getProductsByBrand(Long brandId);

    // Eliminar un producto por su ID
    void deleteProduct(Long id);

    // Reducir el stock de un producto
    void reduceStock(Long productId, int quantity);

    // Activar un producto
    void activateProduct(Long id);

    // Desactivar un producto
    void deactivateProduct(Long id);
}
