package com.EcoHouse.product.services;

import com.EcoHouse.product.model.Product;
import com.EcoHouse.product.dto.ProductResponse;

import java.util.List;

public interface IProductService {

    // Crear un nuevo producto
    Product createProduct(Product product, Long categoryId, Long brandId, List<Long> certificationIds);

    // Actualizar un producto existente
    Product updateProduct(Long id, Product product, Long categoryId, Long brandId, List<Long> certificationIds);

    // Obtener un producto por su ID
    ProductResponse getProductById(Long id);

    // Obtener todos los productos
    List<ProductResponse> getAllProducts();

    // Obtener productos activos
    List<ProductResponse> getActiveProducts();

    // Obtener productos por categoría
    List<ProductResponse> getProductsByCategory(Long categoryId);

    // Obtener productos por marca
    List<ProductResponse> getProductsByBrand(Long brandId);

    // Eliminar un producto por su ID
    void deleteProduct(Long id);

    // Reducir el stock de un producto
    void reduceStock(Long productId, int quantity);

    // Activar un producto
    void activateProduct(Long id);

    // Desactivar un producto
    void deactivateProduct(Long id);
}
