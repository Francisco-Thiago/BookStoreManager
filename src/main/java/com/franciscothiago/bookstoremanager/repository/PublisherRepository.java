package com.franciscothiago.bookstoremanager.repository;

import com.franciscothiago.bookstoremanager.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Optional<Publisher> findByNameOrCode(String nome, String code);
}
