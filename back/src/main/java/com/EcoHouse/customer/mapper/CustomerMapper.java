package com.EcoHouse.customer.mapper;

import org.springframework.stereotype.Component;

import com.EcoHouse.customer.dto.CustomerDTO;
import com.EcoHouse.customer.model.Customer;

@Component
public class CustomerMapper {

    public CustomerDTO toDTO(Customer customer) {
        if (customer == null) return null;

        return CustomerDTO.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .build();
    }

    public Customer toEntity(CustomerDTO dto) {
        if (dto == null) return null;

        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());

        return customer;
    }
}
