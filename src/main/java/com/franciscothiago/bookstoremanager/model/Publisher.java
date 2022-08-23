package com.franciscothiago.bookstoremanager.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Publisher{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String code;

    @Column(nullable = false)
    private String city;
}
