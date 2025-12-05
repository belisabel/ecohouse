package com.EcoHouse.product.controller;

import com.EcoHouse.product.dto.CertificationRequest;
import com.EcoHouse.product.model.Certification;
import com.EcoHouse.product.services.CertificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/certifications")
@RequiredArgsConstructor
public class CertificacionController {

    private final CertificationServiceImpl certificationService;

    @PostMapping
    public ResponseEntity<Certification> createCertification(@RequestBody CertificationRequest request) {
        Certification cert = Certification.builder()
                .name(request.getName())
                .description(request.getDescription())
                .issuedBy(request.getOrganization())
                .website(request.getCertificationUrl())
                .build();

        return ResponseEntity.ok(certificationService.createCertification(cert));
    }

    @GetMapping
    public ResponseEntity<List<Certification>> getAllCertifications() {
        return ResponseEntity.ok(certificationService.getAllCertifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Certification> getCertificationById(@PathVariable Long id) {
        return ResponseEntity.ok(certificationService.getCertificationById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Certification> updateCertification(
            @PathVariable Long id, @RequestBody CertificationRequest request) {

        Certification cert = Certification.builder()
                .name(request.getName())
                .description(request.getDescription())
                .issuedBy(request.getOrganization())
                .website(request.getCertificationUrl())
                .build();

        return ResponseEntity.ok(certificationService.updateCertification(id, cert));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertification(@PathVariable Long id) {
        certificationService.deleteCertification(id);
        return ResponseEntity.noContent().build();
    }



}
