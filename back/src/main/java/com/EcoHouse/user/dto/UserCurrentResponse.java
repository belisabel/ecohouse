package com.EcoHouse.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCurrentResponse {

    private String message;
    private UserResponseDto user;


}
