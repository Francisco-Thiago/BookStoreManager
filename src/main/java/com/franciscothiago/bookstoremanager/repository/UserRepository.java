package com.franciscothiago.bookstoremanager.repository;

import com.franciscothiago.bookstoremanager.enums.Role;
import com.franciscothiago.bookstoremanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findByRole(Role role);

    Optional<User> findByEmail(String email);

    Optional<User> findByIdOrEmail(Long id, String email);
}
