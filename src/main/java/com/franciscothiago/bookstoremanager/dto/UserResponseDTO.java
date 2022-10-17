package com.franciscothiago.bookstoremanager.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;

    private String name;

    private String email;

    private String city;

    private String address;

    private LocalDate registrationDate;

    private String role;

}
