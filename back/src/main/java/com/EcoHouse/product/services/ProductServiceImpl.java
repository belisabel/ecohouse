package com.EcoHouse.product.services;

import com.EcoHouse.category.model.Category;
import com.EcoHouse.category.repository.CategoryRepository;
import com.EcoHouse.product.model.Brand;
import com.EcoHouse.product.model.Certification;
import com.EcoHouse.product.model.Product;
import com.EcoHouse.product.repository.BrandRepository;
import com.EcoHouse.product.repository.CertificationRepository;
import com.EcoHouse.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final CertificationRepository certificationRepository;

    // -------------------------------------------------------------------------
    // CREATE
    // -------------------------------------------------------------------------
    @Override
    public Product createProduct(Product product, Long categoryId, Long brandId, List<Long> certificationIds) {

        // CATEGORY
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // BRAND
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        // CERTIFICATIONS
        List<Certification> certifications = certificationRepository.findAllById(certificationIds);

        if (certifications.size() != certificationIds.size()) {
            throw new RuntimeException("Una o más certificaciones no existen");
        }

        // ASIGNACIÓN
        product.setCategory(category);
        product.setBrand(brand);
        product.setCertifications(certifications);

        return productRepository.save(product);
    }

    // -------------------------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------------------------
    @Override
    public Product updateProduct(Long id, Product data, Long categoryId, Long brandId, List<Long> certificationIds) {
        Product product = getProductById(id);

        product.setName(data.getName());
        product.setDescription(data.getDescription());
        product.setPrice(data.getPrice());
        product.setImageUrl(data.getImageUrl());
        product.setAdditionalImages(data.getAdditionalImages());
        product.setStock(data.getStock());
        product.setEnvironmentalData(data.getEnvironmentalData());
        product.setIsActive(data.getIsActive());

        // CATEGORY
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            product.setCategory(category);
        }

        // BRAND
        if (brandId != null) {
            Brand brand = brandRepository.findById(brandId)
                    .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
            product.setBrand(brand);
        }

        // CERTIFICATIONS
        if (certificationIds != null) {
            List<Certification> certs = certificationRepository.findAllById(certificationIds);
            product.setCertifications(certs);
        }

        return productRepository.save(product);
    }

    // -------------------------------------------------------------------------
    // READ
    // -------------------------------------------------------------------------
    @Override
    public Product getProductById(Long id) {
        // Usar JOIN FETCH para cargar relaciones en una sola query
        return productRepository.findByIdWithRelations(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        // Usar JOIN FETCH para cargar relaciones en una sola query
        // Esto evita LazyInitializationException al serializar en el controller
        return productRepository.findAllWithRelations();
    }

    @Override
    public List<Product> getActiveProducts() {
        return productRepository.findByIsActiveTrue();
    }

     @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

     @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByBrand(Long brandId) {
        return productRepository.findByBrandId(brandId);
    }

    // -------------------------------------------------------------------------
    // DELETE
    // -------------------------------------------------------------------------
    @Override
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    // -------------------------------------------------------------------------
    // STOCK
    // -------------------------------------------------------------------------
    @Override
    public void reduceStock(Long productId, int quantity) {
        Product product = getProductById(productId);

        if (product.getStock() < quantity) {
            throw new RuntimeException("Stock insuficiente: " + product.getName());
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }

    // -------------------------------------------------------------------------
    // ACTIVATION
    // -------------------------------------------------------------------------
    @Override
    public void activateProduct(Long id) {
        Product product = getProductById(id);
        product.setIsActive(true);
        productRepository.save(product);
    }

    @Override
    public void deactivateProduct(Long id) {
        Product product = getProductById(id);
        product.setIsActive(false);
        productRepository.save(product);
    }

}
