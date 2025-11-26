package com.EcoHouse.auth.controller;


import com.EcoHouse.user.dto.LoginRequest;
import com.EcoHouse.user.dto.RegisterRequest;
import com.EcoHouse.user.dto.UserResponseDto;
import com.EcoHouse.user.model.User;
import com.EcoHouse.user.service.BrandAdminService;
import com.EcoHouse.user.service.CustomerService;
import com.EcoHouse.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final CustomerService customerService;
    private final BrandAdminService brandAdminService;



    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody RegisterRequest request){


        User user = userService.createUser(request);


        switch (user.getUserType()) {
            case CUSTOMER -> customerService.createCustomer(user);
            case BRAND_ADMIN -> brandAdminService.createBrandAdmin(user);
        }


        return ResponseEntity.ok(userService.toDto(user));
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {

            String role = authentication.getAuthorities()
                    .stream()
                    .findFirst()
                    .map(a -> a.getAuthority())
                    .orElse("ROLE_CUSTOMER");

            return ResponseEntity.ok("Inicio de sesión correcto: "
                    + authentication.getName()
                    + " (Rol: " + role + ")");
        } else {
            return ResponseEntity.status(401).body("No autenticado");
        }
    }










}
