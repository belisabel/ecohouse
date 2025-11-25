package com.EcoHouse.product.mapper;

import com.EcoHouse.product.dto.CertificationDTO;
import com.EcoHouse.product.model.Certification;

public class CertificationMapper {

    public static CertificationDTO toDTO(Certification cert) {
        if (cert == null) return null;

        return CertificationDTO.builder()
                .id(cert.getId())
                .name(cert.getName())
                .description(cert.getDescription())
                .organization(cert.getIssuedBy())
                .certificationUrl(cert.getWebsite())
                .build();
    }

    public static Certification toEntity(CertificationDTO dto) {
        if (dto == null) return null;

        Certification cert = new Certification();
        cert.setId(dto.getId());
        cert.setName(dto.getName());
        cert.setDescription(dto.getDescription());
        cert.setIssuedBy(dto.getOrganization());
        cert.setWebsite(dto.getCertificationUrl());

        return cert;
    }
}
