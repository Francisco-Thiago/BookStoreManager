package com.franciscothiago.bookstoremanager.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUpdateDTO {

    @NotNull
    @NotEmpty
    @Size(max = 140)
    private String password;

}
