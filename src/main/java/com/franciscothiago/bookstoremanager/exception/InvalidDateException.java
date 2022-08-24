package com.franciscothiago.bookstoremanager.exception;

import java.time.DateTimeException;

public class InvalidDateException extends DateTimeException {

    public InvalidDateException(String date) {
        super(String.format("The date %s is invalid.", date));
    }

    public InvalidDateException(String newExpiration, String newReturn) {
        super(String.format("Date Expiration %s and/or Return Date %s is/are invalid", newExpiration, newReturn));
    }

}
