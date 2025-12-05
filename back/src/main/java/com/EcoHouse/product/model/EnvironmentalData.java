package com.EcoHouse.product.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "environmental_data")
public class EnvironmentalData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Relación bidireccional con Product (un EnvironmentalData pertenece a un Product) */
    @OneToOne(mappedBy = "environmentalData")
    private Product product;

    /** Huella de carbono total del producto en kg de CO₂e */
    @Column(precision = 10, scale = 2)
    private BigDecimal carbonFootprint;

    /** Material principal del producto (madera, vidrio, reciclado, bamboo, etc.) */
    private String material;

    /** País donde fue fabricado */
    private String countryOfOrigin;

    /** Energía consumida durante la fabricación (kWh) */
    @Column(precision = 10, scale = 2)
    private BigDecimal energyConsumption;

    /** Porcentaje de reciclabilidad del producto (0 - 100%) */
    @Column(precision = 5, scale = 2)
    private BigDecimal recyclablePercentage;

    /** Información ambiental adicional */
    @Column(columnDefinition = "TEXT")
    private String notes;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }
}
