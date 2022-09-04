package com.franciscothiago.bookstoremanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    private Long id;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String username;

    @NotNull
    @NotEmpty
    @Email
    @Size(max = 140)
    private String email;

    @NotNull
    @NotEmpty
    @Size(max = 140)
    private String password;

    @NotNull
    @NotEmpty
    private String city;

    @NotNull
    @NotEmpty
    @Size(max = 140)
    private String address;

    @NotNull
    @Size(max = 20)
    private String role;

}
