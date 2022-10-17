package com.franciscothiago.bookstoremanager.exception;

import javax.persistence.EntityExistsException;

public class RentalsAlreadyExistsException extends EntityExistsException {

    private static final long serialVersionUID = 3643401813997254608L;

    public RentalsAlreadyExistsException(Long id) {
        super(String.format("Aluguel com o id %d já existe!", id));
    }

}
