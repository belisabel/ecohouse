package com.EcoHouse.product.services;

import com.EcoHouse.product.model.Certification;
import com.EcoHouse.product.repository.CertificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationServiceImpl implements ICertificationService {

    @Autowired
    private CertificationRepository certificationRepository;

    @Override
    public Certification createCertification(Certification certification) {
        return certificationRepository.save(certification);
    }

    @Override
    public Certification updateCertification(Long id, Certification certification) {
        Certification existing = getCertificationById(id);

        existing.setName(certification.getName());
        existing.setDescription(certification.getDescription());
        existing.setIssuedBy(certification.getIssuedBy());
        existing.setWebsite(certification.getWebsite());

        return certificationRepository.save(existing);
    }

    @Override
    public void deleteCertification(Long id) {
        certificationRepository.deleteById(id);
    }

    @Override
    public Certification getCertificationById(Long id) {
        return certificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certification not found"));
    }

    @Override
    public List<Certification> getAllCertifications() {
        return certificationRepository.findAll();
    }
}
