package com.EcoHouse.user.service;

import com.EcoHouse.user.model.Customer;
import com.EcoHouse.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {


    Customer createCustomer(User user);
    Customer findByUserId(Long userId);

    Page<Customer> getAllCustomers(Pageable pageable);

    Customer getCustomerByEmail(String email);

    Customer getCurrentCustomer();
}
