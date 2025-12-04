package com.EcoHouse.user.service.impl;

import com.EcoHouse.user.dto.CustomerDTO;
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
    public Customer createCustomer(User user) {
        // Crear un Customer copiando los datos del User
        Customer customer = new Customer();
        customer.setEmail(user.getEmail());
        customer.setPassword(user.getPassword());
        customer.setFirstName(user.getFirstName());
        customer.setLastName(user.getLastName());
        customer.setUserType(user.getUserType());
        customer.setCreatedAt(user.getCreatedAt());
        customer.setUpdatedAt(user.getUpdatedAt());
        return customerRepository.save(customer);
    }

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
    public CustomerDTO createCustomer(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setEmail(dto.getEmail());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setShippingAddress(dto.getShippingAddress());
        customer.setBillingAddress(dto.getBillingAddress());
        customer.setPhone(dto.getPhone());
        customer.setCarbonFootprint(dto.getCarbonFootprint());
        customer.setCreatedAt(java.time.LocalDateTime.now());
        customer.setUpdatedAt(java.time.LocalDateTime.now());

        Customer saved = customerRepository.save(customer);
        return toDTO(saved);
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer no encontrado con id: " + id));
        return toDTO(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer no encontrado con id: " + id));

        if (dto.getFirstName() != null) customer.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) customer.setLastName(dto.getLastName());
        if (dto.getEmail() != null) customer.setEmail(dto.getEmail());
        if (dto.getShippingAddress() != null) customer.setShippingAddress(dto.getShippingAddress());
        if (dto.getBillingAddress() != null) customer.setBillingAddress(dto.getBillingAddress());
        if (dto.getPhone() != null) customer.setPhone(dto.getPhone());
        if (dto.getCarbonFootprint() != null) customer.setCarbonFootprint(dto.getCarbonFootprint());

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
    public CustomerDTO getCustomerByEmail(String email, boolean returnDTO) {
        Customer customer = getCustomerByEmail(email);
        return toDTO(customer);
    }

    // Método helper para convertir Customer a CustomerDTO
    private CustomerDTO toDTO(Customer customer) {
        return CustomerDTO.builder()
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
