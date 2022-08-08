package com.franciscothiago.bookstoremanager.entity.user.dto;

import com.franciscothiago.bookstoremanager.entity.user.enums.Gender;
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
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @NotEmpty
    @Max(128)
    private Integer age;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

//    @Column(nullable = false, columnDefinition = "TIMESTAMP")
//    private LocalDate birthDate;
}
