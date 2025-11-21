package com.EcoHouse.user.dto;

import com.EcoHouse.user.model.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String firstName;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String lastName;

    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    private UserType userType;

    @NotBlank(message = "El password no puede estar vacío")
    @Size(min = 6, max = 20, message = "El password debe tener entre 6 y 20 caracteres")
    @NotBlank
    private String password;

}
