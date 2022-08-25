package com.franciscothiago.bookstoremanager.exception;

import javax.persistence.EntityExistsException;

public class BookAlreadyExistsException extends EntityExistsException {



    public BookAlreadyExistsException(Long id, String name, String code) {
        super(String.format("Book with id %d, name %s or code %s, already exists!", id, name, code));
    }

    public BookAlreadyExistsException(Long id, String name) {
        super(String.format("Book with id %d or name %s already exists!", id, name));
    }

    public BookAlreadyExistsException(String name) {
        super(String.format("Book with the name %s already exists!", name));
    }
}
