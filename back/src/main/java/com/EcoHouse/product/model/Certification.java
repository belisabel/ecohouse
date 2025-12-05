package com.EcoHouse.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "certifications")
@Builder
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;  // Ej: "Fair Trade", "Carbon Neutral"

    @Column(columnDefinition = "TEXT")
    private String description;

    private String issuedBy; // Organización certificadora

    private String website; // Info oficial

    @Column(nullable = false)
    private Boolean isActive = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @JsonIgnore // Evita serialización accidental y referencias cíclicas
    @ManyToMany(mappedBy = "certifications", fetch = FetchType.LAZY)
    private List<Product> products; // inversa

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
        if (isActive == null) isActive = true;
    }
}
