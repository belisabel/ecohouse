package com.EcoHouse.user.repository;

import com.EcoHouse.user.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Customer extiende de User, por lo tanto tiene los campos directamente
    Optional<Customer> findByEmail(String email);

}
