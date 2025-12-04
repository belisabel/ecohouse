package com.EcoHouse.product.mapper;

import com.EcoHouse.product.dto.CertificationResponse;
import com.EcoHouse.product.dto.CertificationRequest;
import com.EcoHouse.product.model.Certification;

public class CertificationMapper {

    public static CertificationResponse toDTO(Certification cert) {
        if (cert == null) return null;

        return CertificationResponse.builder()
                .id(cert.getId())
                .name(cert.getName())
                .description(cert.getDescription())
                .organization(cert.getIssuedBy())
                .certificationUrl(cert.getWebsite())
                .build();
    }

    public static Certification toEntity(CertificationRequest request) {
        if (request == null) return null;

        Certification cert = new Certification();
        cert.setName(request.getName());
        cert.setDescription(request.getDescription());
        cert.setIssuedBy(request.getOrganization());
        cert.setWebsite(request.getCertificationUrl());

        return cert;
    }
}
