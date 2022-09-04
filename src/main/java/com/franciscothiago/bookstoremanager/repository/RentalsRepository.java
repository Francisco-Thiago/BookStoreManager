package com.franciscothiago.bookstoremanager.repository;

import com.franciscothiago.bookstoremanager.model.Book;
import com.franciscothiago.bookstoremanager.model.Rentals;
import com.franciscothiago.bookstoremanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalsRepository extends JpaRepository<Rentals, Long> {

    Optional<Rentals> findByUserAndBook(User user, Book book);

}
