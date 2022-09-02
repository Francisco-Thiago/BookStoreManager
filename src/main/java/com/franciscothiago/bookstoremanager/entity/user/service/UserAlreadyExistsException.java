package com.franciscothiago.bookstoremanager.entity.user.service;

import javax.persistence.EntityExistsException;

public class UserAlreadyExistsException extends EntityExistsException {

    public UserAlreadyExistsException(Long id) {
        super(String.format("User with id %s already exists!", id));
    }

    public UserAlreadyExistsException(String email) {
        super(String.format("User with email %s already exists!", email));
    }

    public UserAlreadyExistsException(Long id, String email) {
        super(String.format("User with id %s or email %s already exists!", id, email));
    }

}
