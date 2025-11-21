package com.EcoHouse.user.service;

import com.EcoHouse.user.dto.RegisterRequest;
import com.EcoHouse.user.dto.UserResponseDto;
import com.EcoHouse.user.model.User;

import java.util.Optional;

public interface UserService {

    User createUser(RegisterRequest request);
    Optional<User> findByEmail(String email);
    UserResponseDto toDto(User user);

}
