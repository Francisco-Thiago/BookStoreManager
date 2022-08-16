package com.franciscothiago.bookstoremanager.entity.rentals.service;

import javax.persistence.EntityNotFoundException;

public class RentalsNotFoundException extends EntityNotFoundException {
    public RentalsNotFoundException(Long id) {
        super(String.format("Rentals with id %d not exists!", id));
    }
}
