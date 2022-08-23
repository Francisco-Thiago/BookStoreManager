package com.franciscothiago.bookstoremanager.entity.rentals.dto;


import com.franciscothiago.bookstoremanager.entity.book.Book;
import com.franciscothiago.bookstoremanager.entity.user.User;
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

    private LocalDate entryDate;

    private LocalDate returnDate;

    private LocalDate expirationDate;
}