package com.EcoHouse.product.services;

import com.EcoHouse.category.model.Category;
import com.EcoHouse.category.repository.CategoryRepository;
import com.EcoHouse.product.dto.ProductResponse;
import com.EcoHouse.product.mapper.ProductMapper;
import com.EcoHouse.product.model.Brand;
import com.EcoHouse.product.model.Certification;
import com.EcoHouse.product.model.Product;
import com.EcoHouse.product.repository.BrandRepository;
import com.EcoHouse.product.repository.CertificationRepository;
import com.EcoHouse.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final CertificationRepository certificationRepository;
    private final ProductMapper productMapper;

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
        product.setCertifications(new HashSet<>(certifications));

        return productRepository.save(product);
    }

    // -------------------------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------------------------
    @Override
    public Product updateProduct(Long id, Product data, Long categoryId, Long brandId, List<Long> certificationIds) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setName(data.getName());
        product.setDescription(data.getDescription());
        product.setPrice(data.getPrice());
        product.setImageUrl(data.getImageUrl());
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
            product.setCertifications(new HashSet<>(certs));
        }

        return productRepository.save(product);
    }

    // -------------------------------------------------------------------------
    // READ
    // -------------------------------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findByIdWithRelations(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return productMapper.toDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAllWithRelations().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getActiveProducts() {
        return productRepository.findByIsActiveTrue().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByBrand(Long brandId) {
        return productRepository.findByBrandId(brandId).stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------------------------
    // DELETE
    // -------------------------------------------------------------------------
    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productRepository.delete(product);
    }

    // -------------------------------------------------------------------------
    // STOCK
    // -------------------------------------------------------------------------
    @Override
    public void reduceStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

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
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        product.setIsActive(true);
        productRepository.save(product);
    }

    @Override
    public void deactivateProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        product.setIsActive(false);
        productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsOrderedByCarbonFootprint() {
        return productRepository.findAllOrderByCarbonFootprintAsc()
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getTop5LowestCarbonFootprintProducts() {
        return productRepository
                .findTop5OrderByCarbonFootprintAsc(PageRequest.of(0, 5))
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

}
