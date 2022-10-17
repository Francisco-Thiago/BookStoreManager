package com.franciscothiago.bookstoremanager.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
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
