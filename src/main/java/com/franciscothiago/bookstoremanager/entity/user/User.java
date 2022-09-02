package com.franciscothiago.bookstoremanager.entity.user;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 140)
    private String email;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false, length = 140)
    private String address;
}
