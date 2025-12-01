package com.EcoHouse.user.service.impl;

import com.EcoHouse.user.model.Customer;
import com.EcoHouse.user.model.User;
import com.EcoHouse.user.repository.CustomerRepository;
import com.EcoHouse.user.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByUser_Email(email).orElse(null);
    }

    @Override
    public Customer getCurrentCustomer() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getCustomerByEmail(email);
    }

}
