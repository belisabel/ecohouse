package com.EcoHouse.product.controller;

import com.EcoHouse.product.dto.CertificationRequest;
import com.EcoHouse.product.dto.CertificationResponse;
import com.EcoHouse.product.mapper.CertificationMapper;
import com.EcoHouse.product.model.Certification;
import com.EcoHouse.product.services.CertificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/certifications")
@RequiredArgsConstructor
public class CertificacionController {

    private final CertificationServiceImpl certificationService;

    @PostMapping
    public ResponseEntity<CertificationResponse> createCertification(@RequestBody CertificationRequest request) {
        Certification cert = CertificationMapper.toEntity(request);
        Certification saved = certificationService.createCertification(cert);
        return ResponseEntity.ok(CertificationMapper.toDTO(saved));
    }

    @GetMapping
    public ResponseEntity<List<CertificationResponse>> getAllCertifications() {
        List<CertificationResponse> certifications = certificationService.getAllCertifications()
                .stream()
                .map(CertificationMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(certifications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificationResponse> getCertificationById(@PathVariable Long id) {
        Certification cert = certificationService.getCertificationById(id);
        return ResponseEntity.ok(CertificationMapper.toDTO(cert));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificationResponse> updateCertification(
            @PathVariable Long id, @RequestBody CertificationRequest request) {

        Certification cert = CertificationMapper.toEntity(request);
        Certification updated = certificationService.updateCertification(id, cert);
        return ResponseEntity.ok(CertificationMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertification(@PathVariable Long id) {
        certificationService.deleteCertification(id);
        return ResponseEntity.noContent().build();
    }



}
