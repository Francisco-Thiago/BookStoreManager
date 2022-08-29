package com.franciscothiago.bookstoremanager.dto;

import com.franciscothiago.bookstoremanager.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;

    private String username;

    private String email;

    private String password;

    private String city;

    private String address;

    private LocalDate registrationDate;

    private Role role;
}
