package com.franciscothiago.bookstoremanager.entity.book;

import com.franciscothiago.bookstoremanager.entity.publisher.Publisher;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate release;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;
}
