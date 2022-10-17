package com.franciscothiago.bookstoremanager.exception;

public class InvalidStringException extends RuntimeException{

    private static final long serialVersionUID = 753671636726695487L;

    public InvalidStringException(String message) {
        super(message);
    }

}
