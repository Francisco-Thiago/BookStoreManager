package com.franciscothiago.bookstoremanager.entity.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String name;

    @NotNull
    @NotEmpty
    @Email
    @Size(max = 140)
    private String email;

    @NotNull
    @NotEmpty
    private String city;

    @NotNull
    @NotEmpty
    @Size(max = 140)
    private String address;
}
