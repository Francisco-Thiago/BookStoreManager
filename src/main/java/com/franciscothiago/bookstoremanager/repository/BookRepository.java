package com.franciscothiago.bookstoremanager.repository;

import com.franciscothiago.bookstoremanager.model.Book;
import com.franciscothiago.bookstoremanager.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByPublisher(Publisher publisher);

    Optional<Book> findByIdNameOrCode(Long id, String name, String code);

    Optional<Book> findByName(String name);
}
