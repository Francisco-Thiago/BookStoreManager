package com.franciscothiago.bookstoremanager.exception;

import javax.persistence.EntityExistsException;

public class BookAlreadyExistsException extends EntityExistsException {

    public BookAlreadyExistsException(String name) {
        super(String.format("Livro %s já existe!", name));
    }

}
