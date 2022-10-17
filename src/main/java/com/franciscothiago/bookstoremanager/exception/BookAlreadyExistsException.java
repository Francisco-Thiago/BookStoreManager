package com.franciscothiago.bookstoremanager.exception;

import javax.persistence.EntityExistsException;

public class BookAlreadyExistsException extends EntityExistsException {

    private static final long serialVersionUID = 7671485141337430737L;

    public BookAlreadyExistsException(String name) {
        super(String.format("Livro %s já existe!", name));
    }

}
