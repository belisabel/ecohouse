package com.EcoHouse.impactReport.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDto {
    private Long id;
    private Long customerId;
    private BigDecimal totalAmount;
    private BigDecimal co2Footprint;
    private LocalDateTime orderDate;

    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public BigDecimal getCo2Footprint() { return co2Footprint; }
    public LocalDateTime getOrderDate() { return orderDate; }

    public void setId(Long id) { this.id = id; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public void setCo2Footprint(BigDecimal co2Footprint) { this.co2Footprint = co2Footprint; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
}

