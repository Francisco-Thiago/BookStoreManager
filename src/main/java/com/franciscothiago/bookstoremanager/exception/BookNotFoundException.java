package com.franciscothiago.bookstoremanager.exception;

import javax.persistence.EntityNotFoundException;

public class BookNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 270329697136067809L;

    public BookNotFoundException(Long id) {
        super(String.format("Livro com o id %s não existe!", id));
    }

}
