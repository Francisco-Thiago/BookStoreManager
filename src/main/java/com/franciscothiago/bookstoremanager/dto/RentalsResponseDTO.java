package com.franciscothiago.bookstoremanager.dto;


import com.franciscothiago.bookstoremanager.enums.Status;
import com.franciscothiago.bookstoremanager.model.Book;
import com.franciscothiago.bookstoremanager.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalsResponseDTO {

    private Long id;

    private User user;

    private Book book;

    private Status status;

    private LocalDate entryDate;

    private LocalDate returnDate;

    private LocalDate expirationDate;

}