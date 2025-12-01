package com.EcoHouse.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EcoHouse.customer.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Buscar cliente por email (útil para login o validaciones)
    Customer findByEmail(String email);

    // Verificar si un email ya existe
    boolean existsByEmail(String email);

}

