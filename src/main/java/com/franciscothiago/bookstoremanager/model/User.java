package com.franciscothiago.bookstoremanager.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false, unique = true, length = 140)
    private String email;

    @Column(nullable = false, length = 140)
    private String password;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false, length = 140)
    private String address;

    @Column(nullable = false)
    private LocalDate registrationDate;
}
