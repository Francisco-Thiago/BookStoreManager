package com.franciscothiago.bookstoremanager.entity.book;

import lombok.Data;

import com.franciscothiago.bookstoremanager.entity.publisher.Publisher;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
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
    private LocalDate releases;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Publisher publisher;
}
