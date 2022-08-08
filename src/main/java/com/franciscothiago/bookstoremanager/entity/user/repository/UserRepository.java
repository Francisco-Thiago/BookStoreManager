package com.franciscothiago.bookstoremanager.entity.user.repository;

import com.franciscothiago.bookstoremanager.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEMailOrUsername(String email, String username);

}
