package com.franciscothiago.bookstoremanager.entity.publisher.service;

import javax.persistence.EntityNotFoundException;

public class PublisherNotFoundException extends EntityNotFoundException {
    public PublisherNotFoundException(Long id) {
        super(String.format("Publisher with id %d not exists!", id));
    }
}
