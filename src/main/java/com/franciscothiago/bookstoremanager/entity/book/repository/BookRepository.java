package com.franciscothiago.bookstoremanager.entity.book.repository;

import com.franciscothiago.bookstoremanager.entity.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
