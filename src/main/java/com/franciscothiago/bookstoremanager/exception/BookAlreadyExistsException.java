package com.franciscothiago.bookstoremanager.exception;

import javax.persistence.EntityExistsException;

public class BookAlreadyExistsException extends EntityExistsException {

    public BookAlreadyExistsException(String name) {
        super(String.format("Livro com o nome %s já existe!", name));
    }

    public BookAlreadyExistsException(Long id, String name, String code) {
        super(String.format("Livro com o id %d, nome %s ou código %s, já existe!", id, name, code));
    }

}
