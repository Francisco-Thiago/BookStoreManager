package com.franciscothiago.bookstoremanager.exception;

import javax.persistence.EntityExistsException;

public class RentalsAlreadyExistsException extends EntityExistsException {

    public RentalsAlreadyExistsException(Long id) {
        super(String.format("Aluguel com o id %d já existe!", id));
    }

}
