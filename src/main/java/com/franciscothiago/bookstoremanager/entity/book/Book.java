package com.franciscothiago.bookstoremanager.entity.book;

import lombok.Data;

import com.franciscothiago.bookstoremanager.entity.publisher.Publisher;
import com.franciscothiago.bookstoremanager.entity.user.User;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import java.time.Year;

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
    private Year releases;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Publisher publisher;
}
