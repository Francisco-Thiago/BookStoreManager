package com.franciscothiago.bookstoremanager.exception;

import javax.persistence.EntityNotFoundException;

public class BookNotFoundException extends EntityNotFoundException {

    public BookNotFoundException(Long id) {
        super(String.format("Livro com o id %s não existe!", id));
    }

}
