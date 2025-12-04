package com.EcoHouse.user.controller;


import com.EcoHouse.user.dto.CustomerRequest;
import com.EcoHouse.user.dto.CustomerResponse;
import com.EcoHouse.user.dto.CustomerUpdateRequest;
import com.EcoHouse.user.dto.UserCurrentResponse;
import com.EcoHouse.user.dto.UserResponseDto;
import com.EcoHouse.user.model.Customer;
import com.EcoHouse.user.model.User;
import com.EcoHouse.user.repository.UserRepository;
import com.EcoHouse.user.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customers" , description = "Endpoints para manejo de clientes")
public class CustomerController {

    private final CustomerService customerService;
    private final UserRepository userRepository;

    @Operation(summary = "Obtener todos los Customers(paginado")
    @GetMapping
    public Page<Customer> getAllCustomers(@RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page,size);
        return customerService.getAllCustomers(pageable);
    }

    @GetMapping("/current")
    public UserCurrentResponse getCurrentCustomer() {
        Customer customer = customerService.getCurrentCustomer();
        if (customer == null) {
            return UserCurrentResponse.builder()
                    .message("No hay usuario logueado")
                    .user(null)
                    .build();
        }

        UserResponseDto userDto = UserResponseDto.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .userType(customer.getUserType().name())
                .build();

        String message = "El usuario logueado actualmente es " + customer.getFirstName()
                + " " + customer.getLastName();

        return UserCurrentResponse.builder()
                .message(message)
                .user(userDto)
                .build();
    }


    @Operation(summary = "Obtener customer por email")
    @GetMapping("/by-email")
    public ResponseEntity<?> getByEmail(@RequestParam String email) {
        try {
            Customer customer = customerService.getCustomerByEmail(email);
            return ResponseEntity.ok(customer);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Operation(summary = "Actualizar datos del customer actual")
    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerUpdateRequest request) {
        try {
            Customer updated = customerService.updateCurrentCustomer(request);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ========== Nuevos endpoints CRUD con DTO ==========

    @Operation(summary = "Crear un nuevo customer")
    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody CustomerRequest request) {
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    @Operation(summary = "Obtener customer por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @Operation(summary = "Obtener todos los customers (lista)")
    @GetMapping("/list")
    public ResponseEntity<List<CustomerResponse>> getAll() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @Operation(summary = "Actualizar customer por ID")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable Long id, @RequestBody CustomerRequest request) {
        return ResponseEntity.ok(customerService.updateCustomer(id, request));
    }

    @Operation(summary = "Eliminar customer por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener customer por email (DTO)")
    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerResponse> getByEmailDTO(@PathVariable String email) {
        return ResponseEntity.ok(customerService.getCustomerByEmail(email, true));
    }

}
