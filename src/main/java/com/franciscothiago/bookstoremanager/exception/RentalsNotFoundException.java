package com.franciscothiago.bookstoremanager.exception;

import javax.persistence.EntityNotFoundException;

public class RentalsNotFoundException extends EntityNotFoundException {

    public RentalsNotFoundException(Long id) {
        super(String.format("Aluguel com o id %d não existe!", id));
    }

}
