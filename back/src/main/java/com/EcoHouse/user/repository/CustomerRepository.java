package com.EcoHouse.user.repository;

import com.EcoHouse.user.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    Optional<Customer> findByEmailWithUser(@Param("email") String email);

    @Query("SELECT c FROM Customer c")
    List<Customer> findAllWithUser();

    @Query("SELECT c FROM Customer c")
    Page<Customer> findAllWithUser(Pageable pageable);

    @Query("SELECT c FROM Customer c WHERE c.id = :id")
    Optional<Customer> findByIdWithUser(@Param("id") Long id);
}
