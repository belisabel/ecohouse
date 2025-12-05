package com.EcoHouse.product.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;

    private String country;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    private List<Product> products; // opcional

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
        if (isActive == null) isActive = true;
    }
}
