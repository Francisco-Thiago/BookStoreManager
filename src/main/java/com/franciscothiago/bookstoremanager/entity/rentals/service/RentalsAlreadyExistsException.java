package com.franciscothiago.bookstoremanager.entity.rentals.service;

import javax.persistence.EntityExistsException;

public class RentalsAlreadyExistsException extends EntityExistsException {
    public RentalsAlreadyExistsException(Long id) {
        super(String.format("Rental with id %d already exists!", id));
    }
}
