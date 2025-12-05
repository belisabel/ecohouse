package com.EcoHouse.product.controller;


import com.EcoHouse.product.dto.BrandRequest;
import com.EcoHouse.product.dto.BrandResponse;
import com.EcoHouse.product.mapper.BrandMapper;
import com.EcoHouse.product.model.Brand;
import com.EcoHouse.product.services.IBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final IBrandService brandService;


    // Crear marca
    @PostMapping
    public ResponseEntity<BrandResponse> createBrand(@RequestBody BrandRequest request) {
        Brand brand = new Brand();
        brand.setName(request.getName());
        brand.setDescription(request.getDescription());
        brand.setImageUrl(request.getWebsiteUrl());
        brand.setCountry(request.getCountry());

        Brand saved = brandService.createBrand(brand);

        return ResponseEntity.ok(BrandMapper.toDTO(saved));
    }

    // Listar todas las marcas
    @GetMapping
    public ResponseEntity<List<BrandResponse>> getAllBrands() {
        List<BrandResponse> brands = brandService.getAllBrands()
                .stream()
                .map(BrandMapper::toDTO)
                .toList();

        return ResponseEntity.ok(brands);
    }

    // Obtener marca por ID
    @GetMapping("/{id}")
    public ResponseEntity<BrandResponse> getBrandById(@PathVariable Long id) {
        Brand brand = brandService.getBrandById(id);
        return ResponseEntity.ok(BrandMapper.toDTO(brand));
    }

    // Actualizar marca
    @PutMapping("/{id}")
    public ResponseEntity<BrandResponse> updateBrand(@PathVariable Long id, @RequestBody BrandRequest request) {
        Brand brand = new Brand();
        brand.setName(request.getName());
        brand.setDescription(request.getDescription());
        brand.setImageUrl(request.getWebsiteUrl());
        brand.setCountry(request.getCountry());

        Brand updated = brandService.updateBrand(id, brand);

        return ResponseEntity.ok(BrandMapper.toDTO(updated));
    }

    // Eliminar marca
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }

}
