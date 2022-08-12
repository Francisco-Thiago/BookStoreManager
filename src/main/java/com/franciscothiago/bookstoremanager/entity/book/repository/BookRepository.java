package com.franciscothiago.bookstoremanager.entity.book.repository;

import com.franciscothiago.bookstoremanager.entity.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIdOrName(Long id, String name);

}
