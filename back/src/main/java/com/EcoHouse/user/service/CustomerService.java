package com.EcoHouse.user.service;

import com.EcoHouse.user.dto.CustomerDTO;
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
    CustomerDTO createCustomer(CustomerDTO dto);
    CustomerDTO getCustomerById(Long id);
    List<CustomerDTO> getAllCustomers();
    CustomerDTO updateCustomer(Long id, CustomerDTO dto);
    void deleteCustomer(Long id);
    CustomerDTO getCustomerByEmail(String email, boolean returnDTO);
}
