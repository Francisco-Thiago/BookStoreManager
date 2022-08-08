package com.franciscothiago.bookstoremanager.entity.publisher.repository;

import com.franciscothiago.bookstoremanager.entity.publisher.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Optional<Publisher> findByNameOrCode(String nome, String code);
}
