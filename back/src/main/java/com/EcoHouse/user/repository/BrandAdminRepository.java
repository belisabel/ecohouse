package com.EcoHouse.user.repository;

import com.EcoHouse.user.model.BrandAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandAdminRepository extends JpaRepository<BrandAdmin,Long> {

    Optional<BrandAdmin> findByUser_Id(Long userId);



}
