package com.franciscothiago.bookstoremanager.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDTO {

    private Long id;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String name;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String username;

    @NotNull
    @NotEmpty
    @Email
    @Size(max = 255)
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

}
