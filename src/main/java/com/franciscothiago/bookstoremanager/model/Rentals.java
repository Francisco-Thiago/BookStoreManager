package com.franciscothiago.bookstoremanager.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Rentals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate entryDate;

    @Column(nullable = false)
    private LocalDate returnDate;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
