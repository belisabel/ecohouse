package com.EcoHouse.user.service;

import com.EcoHouse.user.dto.CustomerRequest;
import com.EcoHouse.user.dto.CustomerResponse;
import com.EcoHouse.user.dto.CustomerUpdateRequest;
import com.EcoHouse.user.model.Customer;
import com.EcoHouse.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(User user);
    Customer findByUserId(Long userId);

    Page<Customer> getAllCustomers(Pageable pageable);

    Customer getCustomerByEmail(String email);

    Customer getCurrentCustomer();

    Customer updateCurrentCustomer(CustomerUpdateRequest request);

    // Nuevos métodos CRUD con DTO
    CustomerResponse createCustomer(CustomerRequest request);
    CustomerResponse getCustomerById(Long id);
    List<CustomerResponse> getAllCustomers();
    CustomerResponse updateCustomer(Long id, CustomerRequest request);
    void deleteCustomer(Long id);
    CustomerResponse getCustomerByEmail(String email, boolean returnDTO);
}
