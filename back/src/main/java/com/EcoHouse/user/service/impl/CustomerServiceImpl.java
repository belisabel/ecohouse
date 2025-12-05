package com.EcoHouse.user.service.impl;

import com.EcoHouse.user.dto.CustomerRequest;
import com.EcoHouse.user.dto.CustomerResponse;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;




    @Override
    public Customer findByUserId(Long userId) {
        // Customer extiende de User, por lo tanto el userId es el id del Customer
        return customerRepository.findById(userId).orElse(null);
    }

    @Override
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        // Buscar Customer directamente por email (Customer extiende de User)
        return customerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("No existe un Customer con el email: " + email)
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

        // 2. Actualizar campos del usuario (Customer extiende de User)
        if (request.getFirstName() != null) {
            customer.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            customer.setLastName(request.getLastName());
        }

        // 3. Actualizar campos específicos de customer
        if (request.getShippingAddress() != null) {
            customer.setShippingAddress(request.getShippingAddress());
        }
        if (request.getCarbonFootprint() != null) {
            customer.setCarbonFootprint(request.getCarbonFootprint());
        }

        // 4. Guardar cambios
        customer.setUpdatedAt(java.time.LocalDateTime.now());
        customerRepository.save(customer);

        return customer;
    }

    // ========== Nuevos métodos CRUD con DTO ==========

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        // Validar que el email no exista
        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con el email: " + request.getEmail());
        }

        Customer customer = new Customer();
        customer.setEmail(request.getEmail());
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setShippingAddress(request.getShippingAddress());
        customer.setBillingAddress(request.getBillingAddress());
        customer.setPhone(request.getPhone());
        customer.setCarbonFootprint(request.getCarbonFootprint());
        customer.setPassword("TEMP_PASSWORD"); // TODO: Debería venir encriptado
        customer.setUserType(com.EcoHouse.user.model.UserType.CUSTOMER);
        customer.setIsActive(true);
        customer.setCreatedAt(java.time.LocalDateTime.now());
        customer.setUpdatedAt(java.time.LocalDateTime.now());

        // JPA guardará automáticamente en users Y customers con el mismo ID
        Customer saved = customerRepository.save(customer);
        return toDTO(saved);
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer no encontrado con id: " + id));
        return toDTO(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer no encontrado con id: " + id));

        if (request.getFirstName() != null) customer.setFirstName(request.getFirstName());
        if (request.getLastName() != null) customer.setLastName(request.getLastName());
        if (request.getEmail() != null) customer.setEmail(request.getEmail());
        if (request.getShippingAddress() != null) customer.setShippingAddress(request.getShippingAddress());
        if (request.getBillingAddress() != null) customer.setBillingAddress(request.getBillingAddress());
        if (request.getPhone() != null) customer.setPhone(request.getPhone());
        if (request.getCarbonFootprint() != null) customer.setCarbonFootprint(request.getCarbonFootprint());

        customer.setUpdatedAt(java.time.LocalDateTime.now());
        Customer updated = customerRepository.save(customer);
        return toDTO(updated);
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer no encontrado con id: " + id);
        }
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerResponse getCustomerByEmail(String email, boolean returnDTO) {
        Customer customer = getCustomerByEmail(email);
        return toDTO(customer);
    }

    // Método helper para convertir Customer a CustomerResponse
    private CustomerResponse toDTO(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .shippingAddress(customer.getShippingAddress())
                .billingAddress(customer.getBillingAddress())
                .phone(customer.getPhone())
                .carbonFootprint(customer.getCarbonFootprint())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

}
