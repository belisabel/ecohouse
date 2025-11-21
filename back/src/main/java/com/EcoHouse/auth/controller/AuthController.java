package com.EcoHouse.auth.controller;


import com.EcoHouse.user.dto.LoginRequest;
import com.EcoHouse.user.dto.RegisterRequest;
import com.EcoHouse.user.dto.UserResponseDto;
import com.EcoHouse.user.model.User;
import com.EcoHouse.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody RegisterRequest request){
        User user = userService.createUser(request);
        return ResponseEntity.ok(userService.toDto(user));
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

            String role = isAdmin ? "ADMIN" : "CUSTOMER";
            return ResponseEntity.ok("Inicio de sesión correcto: " + authentication.getName() + " (Rol: " + role + ")");
        } else {
            return ResponseEntity.status(401).body("No autenticado");
        }
    }








}
