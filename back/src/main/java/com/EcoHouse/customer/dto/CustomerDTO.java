package com.EcoHouse.customer.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
