package com.franciscothiago.bookstoremanager.repository;

import com.franciscothiago.bookstoremanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdOrEmail(Long id, String email);

    Optional<User> findByEmail(String email);

}
