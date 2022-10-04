package com.franciscothiago.bookstoremanager.model;

import com.franciscothiago.bookstoremanager.enums.Role;
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

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 140)
    private String password;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false, length = 140)
    private String address;

    @Column(nullable = false)
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

}
