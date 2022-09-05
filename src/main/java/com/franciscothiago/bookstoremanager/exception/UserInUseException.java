package com.franciscothiago.bookstoremanager.exception;

public class UserInUseException extends RuntimeException {

    private static final long serialVersionUID = 7022467972374607762L;

    public UserInUseException(String message) {
        super(message);
    }

    public UserInUseException(String message, Throwable cause) {
        super(message, cause);
    }

}
