package com.EcoHouse.impactReport.mapper;

import com.EcoHouse.impactReport.dtoResponse.ImpactReportResponseDto;
import com.EcoHouse.impactReport.entities.ImpactReport;
import org.springframework.stereotype.Component;

@Component
public class ImpactReportMapper {

    public ImpactReportResponseDto toResponseDto(ImpactReport entity) {
        if (entity == null) return null;
        ImpactReportResponseDto dto = ImpactReportResponseDto.builder()
                .id(entity.getId())
                .customerId(entity.getCustomerId())
                .startDate(entity.getStartDate() != null ? entity.getStartDate().atStartOfDay() : null)
                .endDate(entity.getEndDate() != null ? entity.getEndDate().atStartOfDay() : null)
                .totalCO2Saved(entity.getTotalCO2Saved())
                .totalCO2Footprint(entity.getTotalCO2Footprint())
                .totalOrders(entity.getTotalOrders())
                .ecoPointsEarned(entity.getEcoPointsEarned())
                .totalAmountSpent(entity.getTotalAmountSpent())
                .sustainableChoicesCount(entity.getSustainableChoicesCount())
                .reportType(entity.getReportType())
                .createdAt(entity.getCreatedAt())
                .build();

        // categoryImpactBreakdown and monthlyTrend are stored as JSON strings in the entity; leave null for now
        return dto;
    }

    public ImpactReport toEntity(ImpactReportResponseDto dto) {
        if (dto == null) return null;
        ImpactReport entity = ImpactReport.builder()
                .customerId(dto.getCustomerId())
                .startDate(dto.getStartDate() != null ? dto.getStartDate().toLocalDate() : null)
                .endDate(dto.getEndDate() != null ? dto.getEndDate().toLocalDate() : null)
                .totalCO2Saved(dto.getTotalCO2Saved())
                .totalCO2Footprint(dto.getTotalCO2Footprint())
                .totalOrders(dto.getTotalOrders())
                .ecoPointsEarned(dto.getEcoPointsEarned())
                .totalAmountSpent(dto.getTotalAmountSpent())
                .sustainableChoicesCount(dto.getSustainableChoicesCount())
                .reportType(dto.getReportType())
                .build();
        return entity;
    }

    public void updateEntityFromDto(ImpactReportResponseDto dto, ImpactReport entity) {
        if (dto == null || entity == null) return;
        if (dto.getCustomerId() != null) entity.setCustomerId(dto.getCustomerId());
        if (dto.getStartDate() != null) entity.setStartDate(dto.getStartDate().toLocalDate());
        if (dto.getEndDate() != null) entity.setEndDate(dto.getEndDate().toLocalDate());
        if (dto.getTotalCO2Saved() != null) entity.setTotalCO2Saved(dto.getTotalCO2Saved());
        if (dto.getTotalCO2Footprint() != null) entity.setTotalCO2Footprint(dto.getTotalCO2Footprint());
        if (dto.getTotalOrders() != null) entity.setTotalOrders(dto.getTotalOrders());
        if (dto.getEcoPointsEarned() != null) entity.setEcoPointsEarned(dto.getEcoPointsEarned());
        if (dto.getTotalAmountSpent() != null) entity.setTotalAmountSpent(dto.getTotalAmountSpent());
        if (dto.getSustainableChoicesCount() != null) entity.setSustainableChoicesCount(dto.getSustainableChoicesCount());
        if (dto.getReportType() != null) entity.setReportType(dto.getReportType());
        // leave createdAt/updatedAt/id/isActive untouched
    }
}