package com.EcoHouse.user.service.impl;

import com.EcoHouse.user.dto.RegisterRequest;
import com.EcoHouse.user.dto.UserResponseDto;
import com.EcoHouse.user.model.BrandAdmin;
import com.EcoHouse.user.model.Customer;
import com.EcoHouse.user.model.User;
import com.EcoHouse.user.model.UserType;
import com.EcoHouse.user.repository.UserRepository;
import com.EcoHouse.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService  {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;




    @Override
    public User createUser(RegisterRequest request) {

        User user;

        // 1. Crear instancia según tipo
        if (request.getUserType() == UserType.BRAND_ADMIN) {
            user = new BrandAdmin();
        } else if (request.getUserType() == UserType.CUSTOMER) {
            user = new Customer();
        } else {
            user = new User(); // Solo admin
        }

        // 2. Setear datos comunes
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUserType(request.getUserType());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);  // JPA se encarga de guardar en ambas tablas
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .userType(user.getUserType() != null ? user.getUserType().name() : null)
                .build();
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

}
