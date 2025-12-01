package com.EcoHouse.product.services;

import com.EcoHouse.product.model.Brand;

import java.util.List;

public interface IBrandService {

    // Crear una nueva marca
    Brand createBrand(Brand brand);

    // Actualizar una marca existente
    Brand updateBrand(Long id, Brand brand);

    // Eliminar una marca por su ID
    void deleteBrand(Long id);

    // Obtener una marca por su ID
    Brand getBrandById(Long id);

    // Obtener todas las marcas
    List<Brand> getAllBrands();
}
