package com.EcoHouse.user.service;

import com.EcoHouse.user.model.Customer;
import com.EcoHouse.user.model.User;

public interface CustomerService {


    Customer createCustomer(User user);
    Customer findByUserId(Long userId);

}
