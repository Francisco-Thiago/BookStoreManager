package com.franciscothiago.bookstoremanager.entity.rentals.service;

import java.time.DateTimeException;

public class InvalidDateException extends DateTimeException {

    public InvalidDateException(String newExpiration, String newReturn) {
        super(String.format("Date Expiration %s and/or Return Date %s is/are invalid", newExpiration, newReturn));
    }

}