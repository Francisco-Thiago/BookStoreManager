package com.franciscothiago.bookstoremanager.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublisherResponseDTO {

    private Long id;

    private String name;

    private String code;

    private String city;

    private LocalDate registrationDate;

}