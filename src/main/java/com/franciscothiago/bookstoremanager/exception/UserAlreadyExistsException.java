package com.franciscothiago.bookstoremanager.exception;

import javax.persistence.EntityExistsException;

public class UserAlreadyExistsException extends EntityExistsException {

    public UserAlreadyExistsException(String value) {
        super(String.format("Usuário com valor %s já existe!", value));
    }

    public UserAlreadyExistsException(Long id, String email) {
        super(String.format("Usuário com id %s ou email %s já existe!", id, email));
    }

}
