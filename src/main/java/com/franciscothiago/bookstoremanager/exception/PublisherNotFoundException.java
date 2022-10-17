package com.franciscothiago.bookstoremanager.exception;

import javax.persistence.EntityNotFoundException;

public class PublisherNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 4166847382592064694L;

    public PublisherNotFoundException(Long id) {
        super(String.format("Editora com o id %d não existe!", id));
    }

}
