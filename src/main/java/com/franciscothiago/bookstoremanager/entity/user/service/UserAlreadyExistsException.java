package com.franciscothiago.bookstoremanager.entity.user.service;

import javax.persistence.EntityExistsException;

public class UserAlreadyExistsException extends EntityExistsException {

    public UserAlreadyExistsException(String email) {
        super(String.format("User with email %s or name %s already exists!", email));
    }

}
