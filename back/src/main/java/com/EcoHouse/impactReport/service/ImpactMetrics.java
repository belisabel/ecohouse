package com.EcoHouse.impactReport.service;

import java.math.BigDecimal;

public class ImpactMetrics {
    private BigDecimal totalCO2Saved = BigDecimal.ZERO;
    private BigDecimal totalCO2Footprint = BigDecimal.ZERO;
    private Integer totalEcoPoints = 0;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private Integer sustainableChoicesCount = 0;

    public BigDecimal getTotalCO2Saved() { return totalCO2Saved; }
    public BigDecimal getTotalCO2Footprint() { return totalCO2Footprint; }
    public Integer getTotalEcoPoints() { return totalEcoPoints; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public Integer getSustainableChoicesCount() { return sustainableChoicesCount; }

    // setters for potential use
    public void setTotalCO2Saved(BigDecimal v) { this.totalCO2Saved = v; }
    public void setTotalCO2Footprint(BigDecimal v) { this.totalCO2Footprint = v; }
    public void setTotalEcoPoints(Integer v) { this.totalEcoPoints = v; }
    public void setTotalAmount(BigDecimal v) { this.totalAmount = v; }
    public void setSustainableChoicesCount(Integer v) { this.sustainableChoicesCount = v; }
}

