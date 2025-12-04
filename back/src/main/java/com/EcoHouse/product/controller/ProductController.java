package com.EcoHouse.product.controller;

import com.EcoHouse.product.dto.ProductResponse;
import com.EcoHouse.product.dto.ProductRequest;
import com.EcoHouse.product.mapper.ProductMapper;
import com.EcoHouse.product.mapper.EnvironmentalDataMapper;
import com.EcoHouse.product.model.Product;
import com.EcoHouse.product.services.ProductServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:3000"
})
public class ProductController {

    private final ProductServiceImpl productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        Product entity = ProductMapper.toEntity(request);

        if (request.getEnvironmentalData() != null) {
            entity.setEnvironmentalData(EnvironmentalDataMapper.toEntity(request.getEnvironmentalData()));
        }

        Product saved = productService.createProduct(
                entity,
                request.getCategoryId(),
                request.getBrandId(),
                request.getCertificationIds()
        );

        return ResponseEntity.ok(ProductMapper.toDTO(saved));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        Product found = productService.getProductById(id);
        return ResponseEntity.ok(ProductMapper.toDTO(found));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
        Product entity = ProductMapper.toEntity(request);

        if (request.getEnvironmentalData() != null) {
            entity.setEnvironmentalData(EnvironmentalDataMapper.toEntity(request.getEnvironmentalData()));
        }

        Product updated = productService.updateProduct(
                id,
                entity,
                request.getCategoryId(),
                request.getBrandId(),
                request.getCertificationIds()
        );

        return ResponseEntity.ok(ProductMapper.toDTO(updated));
    }

    @GetMapping("/brand/{brandId}")
    public ResponseEntity<List<ProductResponse>> getByBrand(@PathVariable Long brandId) {
        List<ProductResponse> list = productService.getProductsByBrand(brandId)
                .stream()
                .map(ProductMapper::toDTO)
                .toList();

        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
