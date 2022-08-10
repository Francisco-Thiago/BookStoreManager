package com.franciscothiago.bookstoremanager.entity.rentals.repository;

import com.franciscothiago.bookstoremanager.entity.rentals.Rentals;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalsRepository extends JpaRepository<Rentals, Long> {
}
