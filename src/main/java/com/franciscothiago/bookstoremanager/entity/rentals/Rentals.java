package com.franciscothiago.bookstoremanager.entity.rentals;

import com.franciscothiago.bookstoremanager.entity.user.User;
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

    @ManyToOne(cascade = {CascadeType.MERGE})
    private User user;
}
