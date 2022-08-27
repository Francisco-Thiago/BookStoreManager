package com.franciscothiago.bookstoremanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublisherResponseDTO {

    private Long id;

    private String name;

    private String code;

    private String city;

    private LocalDate registrationDate;

}