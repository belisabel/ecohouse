package com.EcoHouse.product.controller;

import com.EcoHouse.product.dto.ProductDTO;
import com.EcoHouse.product.mapper.ProductMapper;
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

    // ---------------------------------------------------------------------
    // CREATE
    // ---------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        Product entity = ProductMapper.toEntity(productDTO);

        Product saved = productService.createProduct(
                entity,
                productDTO.getCategoryId(),
                productDTO.getBrandId(),
                productDTO.getCertificationIds()
        );

        return ResponseEntity.ok(ProductMapper.toDTO(saved));
    }

    // ---------------------------------------------------------------------
    // READ ALL
    // ---------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();

        return ResponseEntity.ok(products);
    }

    // ---------------------------------------------------------------------
    // READ ONE
    // ---------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Product found = productService.getProductById(id);
        return ResponseEntity.ok(ProductMapper.toDTO(found));
    }

    // ---------------------------------------------------------------------
    // UPDATE
    // ---------------------------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product entity = ProductMapper.toEntity(productDTO);

        Product updated = productService.updateProduct(
                id,
                entity,
                productDTO.getCategoryId(),
                productDTO.getBrandId(),
                productDTO.getCertificationIds()
        );

        return ResponseEntity.ok(ProductMapper.toDTO(updated));
    }

    // ---------------------------------------------------------------------
    // READ BY BRAND
    // ---------------------------------------------------------------------
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<List<ProductDTO>> getByBrand(@PathVariable Long brandId) {
        List<ProductDTO> list = productService.getProductsByBrand(brandId)
                .stream()
                .map(ProductMapper::toDTO)
                .toList();

        return ResponseEntity.ok(list);
    }

    // ---------------------------------------------------------------------
    // DELETE
    // ---------------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
