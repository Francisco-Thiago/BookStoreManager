package com.franciscothiago.bookstoremanager.entity.book.dto;

import com.franciscothiago.bookstoremanager.entity.publisher.dto.PublisherRequestDTO;
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

    private String author;

    private PublisherRequestDTO publisher;

}
