package com.franciscothiago.bookstoremanager.repository;

import com.franciscothiago.bookstoremanager.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByIdOrEmailOrUsername(Long id, String email, String username);

    Optional<User> findByIdOrEmail(Long id, String email);
}
