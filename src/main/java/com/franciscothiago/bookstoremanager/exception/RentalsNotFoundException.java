package com.franciscothiago.bookstoremanager.exception;

import javax.persistence.EntityNotFoundException;

public class RentalsNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 6300369914323328348L;

    public RentalsNotFoundException(Long id) {
        super(String.format("Aluguel com o id %d não existe!", id));
    }

}
