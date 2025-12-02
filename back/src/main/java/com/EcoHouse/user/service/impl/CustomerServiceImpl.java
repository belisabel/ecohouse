package com.EcoHouse.user.service.impl;

import com.EcoHouse.user.dto.CustomerUpdateRequest;
import com.EcoHouse.user.model.Customer;
import com.EcoHouse.user.model.User;
import com.EcoHouse.user.repository.CustomerRepository;
import com.EcoHouse.user.repository.UserRepository;
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
    private final UserRepository userRepository;


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

        // 1. Buscar usuario por email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("No existe un usuario con el email: " + email)
                );

        // 2. Revisar si ese usuario está registrado como Customer
        return customerRepository.findByUser_Email(email)
                .orElseThrow(() ->
                        new RuntimeException("El usuario con email " + email +
                                " NO es un customer. Su rol actual es: " + user.getUserType())
                );
    }

    @Override
    public Customer getCurrentCustomer() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getCustomerByEmail(email);
    }

    @Override
    public Customer updateCurrentCustomer(CustomerUpdateRequest request) {

        // 1. Obtener customer actual según el usuario autenticado
        Customer customer = getCurrentCustomer();
        if (customer == null) {
            throw new RuntimeException("No se encontró el cliente asociado al usuario actual.");
        }

        // 2. Obtener el usuario asociado
        User user = customer.getUser();

        // 3. Actualizar campos del usuario
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        // 4. Actualizar campos de customer
        if (request.getShippingAddress() != null) {
            customer.setShippingAddress(request.getShippingAddress());
        }
        if (request.getCarbonFootprint() != null) {
            customer.setCarbonFootprint(request.getCarbonFootprint());
        }

        // 5. Guardar cambios
        // ⚠️ Primero guardamos user (no tiene cascade)
        customer.getUser().setUpdatedAt(java.time.LocalDateTime.now());
        customerRepository.save(customer);

        return customer;
    }

}
