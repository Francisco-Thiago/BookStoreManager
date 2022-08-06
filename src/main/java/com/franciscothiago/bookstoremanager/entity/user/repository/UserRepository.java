package com.franciscothiago.bookstoremanager.entity.user.repository;

import com.franciscothiago.bookstoremanager.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
