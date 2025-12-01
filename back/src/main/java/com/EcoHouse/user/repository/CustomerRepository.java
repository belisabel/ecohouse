package com.EcoHouse.user.repository;

import com.EcoHouse.user.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


    Optional<Customer> findByUser_Id(Long userId);

    Optional<Customer> findByUser_Email(String email);


}
