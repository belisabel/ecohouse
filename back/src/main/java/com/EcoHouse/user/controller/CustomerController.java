package com.EcoHouse.user.controller;


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
                .id(customer.getUser().getId())
                .firstName(customer.getUser().getFirstName())
                .lastName(customer.getUser().getLastName())
                .email(customer.getUser().getEmail())
                .userType(customer.getUser().getUserType().name())
                .build();

        String message = "El usuario logueado actualmente es " + customer.getUser().getFirstName()
                + " " + customer.getUser().getLastName();

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


    

}
