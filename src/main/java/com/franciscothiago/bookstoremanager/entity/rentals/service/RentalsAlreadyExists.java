package com.franciscothiago.bookstoremanager.entity.rentals.service;

import javax.persistence.EntityExistsException;

public class RentalsAlreadyExists extends EntityExistsException {
    public RentalsAlreadyExists(Long id) {
        super(String.format("Rental with id %d already exists!", id));
    }
}
