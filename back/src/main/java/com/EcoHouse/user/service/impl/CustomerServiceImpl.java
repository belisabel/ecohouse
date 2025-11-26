package com.EcoHouse.user.service.impl;

import com.EcoHouse.user.model.Customer;
import com.EcoHouse.user.model.User;
import com.EcoHouse.user.repository.CustomerRepository;
import com.EcoHouse.user.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(User user) {
        Customer customer = new Customer();
        customer.setUser(user);
        return customerRepository.save(customer);
    }

    @Override
    public Customer findByUserId(Long userId) {
        return customerRepository.findByUser_Id(userId).orElse(null);
    }

}
