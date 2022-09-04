package com.franciscothiago.bookstoremanager.exception;

import javax.persistence.EntityExistsException;

public class UserAlreadyExistsException extends EntityExistsException {

    public UserAlreadyExistsException(Long id) {
        super(String.format("User with id %s already exists!", id));
    }

    public UserAlreadyExistsException(String value) {
        super(String.format("User with value %s already exists!", value));
    }

    public UserAlreadyExistsException(Long id, String email, String username) {
        super(String.format("User with id %s, email %s or username %s already exists!", id, email, username));
    }

}
