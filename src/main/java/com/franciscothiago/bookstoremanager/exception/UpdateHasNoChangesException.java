package com.franciscothiago.bookstoremanager.exception;

public class UpdateHasNoChangesException extends RuntimeException {

    private static final long serialVersionUID = 798556946957415176L;

    public UpdateHasNoChangesException(String message) {
        super(message);
    }

}
