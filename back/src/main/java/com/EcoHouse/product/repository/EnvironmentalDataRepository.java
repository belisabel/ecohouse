package com.EcoHouse.product.repository;

import com.EcoHouse.product.model.EnvironmentalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentalDataRepository extends JpaRepository<EnvironmentalData, Long> {
}
