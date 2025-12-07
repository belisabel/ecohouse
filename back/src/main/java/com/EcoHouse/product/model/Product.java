package com.EcoHouse.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.EcoHouse.category.model.Category;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    private String imageUrl;

    /** Lista de imágenes adicionales */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "product_additional_images",
            joinColumns = @JoinColumn(name = "product_id")
    )
    private List<String> additionalImages;

    @Column(nullable = false)
    private Integer stock;

    @JsonIgnore // Evita LazyInitializationException
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @JsonIgnore // Evita LazyInitializationException
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore // Evita LazyInitializationException
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "environmental_data_id")
    private EnvironmentalData environmentalData;

    @JsonIgnore // Evita LazyInitializationException y referencias cíclicas
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_certifications",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "certification_id")
    )
    private List<Certification> certifications;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
        if (isActive == null) isActive = true;
    }
}
