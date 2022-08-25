package com.franciscothiago.bookstoremanager.exception;

public class InvalidStringException extends RuntimeException{

    private static final long serialVersionUID = 1149241039409861919L;

    public InvalidStringException(String message) {
        super(message);
    }

    public InvalidStringException(String message, Throwable cause) {
        super(message, cause);
    }
}
