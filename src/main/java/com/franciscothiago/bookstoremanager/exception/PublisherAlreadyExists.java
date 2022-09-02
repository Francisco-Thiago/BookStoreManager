package com.franciscothiago.bookstoremanager.exception;

import javax.persistence.EntityExistsException;

public class PublisherAlreadyExists extends EntityExistsException {
    public PublisherAlreadyExists(String name, String code) {
        super(String.format("Publisher %s or code %s already exists", name, code));
    }
}
