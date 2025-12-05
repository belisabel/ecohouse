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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final CustomerService customerService;
    private final BrandAdminService brandAdminService;
    private final PasswordEncoder passwordEncoder;



    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody RegisterRequest request) {

        User user = userService.createUser(request);

        return ResponseEntity.ok(userService.toDto(user));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequest request) {
        return userService.findByEmail(request.getEmail())
                .filter(u -> passwordEncoder.matches(request.getPassword(), u.getPassword()))
                .map(u -> ResponseEntity.ok(userService.toDto(u))) // devuelve usuario con rol
                .orElse(ResponseEntity.status(401).build());
    }











}
