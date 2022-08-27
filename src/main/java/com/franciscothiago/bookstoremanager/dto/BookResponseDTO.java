package com.franciscothiago.bookstoremanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {

    private Long id;

    private String name;

    private String code;

    private LocalDate release;

    private LocalDate changeDate;

    private String author;

    private PublisherRequestDTO publisher;

}
