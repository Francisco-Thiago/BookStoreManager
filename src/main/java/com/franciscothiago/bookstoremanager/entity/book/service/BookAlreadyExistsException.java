package com.franciscothiago.bookstoremanager.entity.book.service;

import javax.persistence.EntityExistsException;

public class BookAlreadyExistsException extends EntityExistsException {
    public BookAlreadyExistsException(Long id, String name) {
        super(String.format("Book with id %d or name %s already exists!", id, name));
    }
}
