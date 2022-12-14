package com.franciscothiago.bookstoremanager.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {

    private Long id;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String name;

    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String code;

    @NotNull
    private int quantity;

    @NotNull
    @NotEmpty
    @Size(max = 140)
    private String author;

    @NotNull
    private Long publisherId;

}
