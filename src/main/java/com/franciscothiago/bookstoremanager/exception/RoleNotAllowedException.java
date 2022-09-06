package com.franciscothiago.bookstoremanager.exception;

public class RoleNotAllowedException extends RuntimeException {

    private static final long serialVersionUID = 3171150974620370501L;

    public RoleNotAllowedException(String message) {
        super(message);
    }

    public RoleNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

}
