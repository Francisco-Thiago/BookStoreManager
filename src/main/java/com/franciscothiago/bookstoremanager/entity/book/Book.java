package com.franciscothiago.bookstoremanager.entity.book;

import com.franciscothiago.bookstoremanager.entity.author.Author;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;


@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String isbn;

    @Column(columnDefinition = "integer default 0")
    private int pages;

    @Column(columnDefinition = "integer default 0")
    private int chapters;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Author author;
}
