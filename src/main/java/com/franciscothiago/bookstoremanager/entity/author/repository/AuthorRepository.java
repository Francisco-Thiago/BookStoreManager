package com.franciscothiago.bookstoremanager.entity.author.repository;

import com.franciscothiago.bookstoremanager.entity.author.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
