package com.franciscothiago.bookstoremanager.entity.book;

import com.franciscothiago.bookstoremanager.entity.rentals.Rentals;
import lombok.Data;

import com.franciscothiago.bookstoremanager.entity.publisher.Publisher;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false, length = 140)
    private String author;

    @Column(nullable = false)
    private LocalDate releases;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rentals> rentals;
}
