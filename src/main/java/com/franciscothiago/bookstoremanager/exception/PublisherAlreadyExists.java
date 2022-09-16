package com.franciscothiago.bookstoremanager.exception;

import javax.persistence.EntityExistsException;

public class PublisherAlreadyExists extends EntityExistsException {

    public PublisherAlreadyExists(String name, String code) {
        super(String.format("Editora %s ou código %s já existe!", name, code));
    }

}
