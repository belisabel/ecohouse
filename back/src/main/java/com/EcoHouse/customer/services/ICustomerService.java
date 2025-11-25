package com.EcoHouse.customer.services;

import java.util.List;

import com.EcoHouse.customer.dto.CustomerDTO;

public interface ICustomerService {

    CustomerDTO createCustomer(CustomerDTO dto);

    CustomerDTO getCustomerById(Long id);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO updateCustomer(Long id, CustomerDTO dto);

    void deleteCustomer(Long id);

    CustomerDTO getCustomerByEmail(String email);
}
