package com.franciscothiago.bookstoremanager.entity.author;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "Integer default 0")
    private int age;
}
