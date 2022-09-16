package com.franciscothiago.bookstoremanager.exception;

import java.time.DateTimeException;

public class InvalidDateException extends DateTimeException {

    public InvalidDateException(String date) {
        super(String.format("A data %s é inválida.", date));
    }

    public InvalidDateException(String newExpiration, String newReturn) {
        super(String.format("Data de expiração %s e/ou data de retorno %s é/são inválidas.", newExpiration, newReturn));
    }

}
