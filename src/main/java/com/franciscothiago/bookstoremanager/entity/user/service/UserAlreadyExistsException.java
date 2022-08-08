package com.franciscothiago.bookstoremanager.entity.user.service;

import javax.persistence.EntityExistsException;

public class UserAlreadyExistsException extends EntityExistsException {

    public UserAlreadyExistsException(String email, String username) {
        super(String.format("User with email %s or username %s already exists!", email, username));
    }

}
