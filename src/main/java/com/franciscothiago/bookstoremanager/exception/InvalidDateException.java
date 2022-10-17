package com.franciscothiago.bookstoremanager.exception;

import java.time.DateTimeException;

public class InvalidDateException extends DateTimeException {

    public InvalidDateException(String date) {
        super(String.format("A data %s é inválida.", date));
    }

}
