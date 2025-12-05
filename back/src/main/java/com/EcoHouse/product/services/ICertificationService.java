package com.EcoHouse.product.services;

import com.EcoHouse.product.model.Certification;

import java.util.List;

public interface ICertificationService {

    // Crear certificación
    Certification createCertification(Certification certification);

    // Actualizar certificación
    Certification updateCertification(Long id, Certification certification);

    // Eliminar certificación
    void deleteCertification(Long id);

    // Obtener certificación por ID
    Certification getCertificationById(Long id);

    // Obtener todas las certificaciones
    List<Certification> getAllCertifications();
}
