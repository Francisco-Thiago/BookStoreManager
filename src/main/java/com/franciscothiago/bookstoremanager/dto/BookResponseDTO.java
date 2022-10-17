package com.franciscothiago.bookstoremanager.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {

    private Long id;

    private String name;

    private String code;

    private int quantity;

    private LocalDate release;

    private LocalDate changeDate;

    private String author;

    private PublisherRequestDTO publisher;

}
