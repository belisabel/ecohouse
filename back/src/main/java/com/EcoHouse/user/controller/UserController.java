package com.EcoHouse.user.controller;


import com.EcoHouse.user.dto.UpdateUserRequest;
import com.EcoHouse.user.dto.UserResponseDto;
import com.EcoHouse.user.model.User;
import com.EcoHouse.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/profile")
    public ResponseEntity<UserResponseDto> updateProfile(
            @RequestBody UpdateUserRequest request,
            Principal principal   // ← trae el email del usuario logueado
    ) {
        String email = principal.getName(); // Email usado en BasicAuth

        User updated = userService.updateUserByEmail(email, request);

        return ResponseEntity.ok(userService.toDto(updated));
    }



}
