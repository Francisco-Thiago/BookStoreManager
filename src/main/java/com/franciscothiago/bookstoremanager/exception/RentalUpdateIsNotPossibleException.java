package com.franciscothiago.bookstoremanager.exception;

public class RentalUpdateIsNotPossibleException extends RuntimeException {

    private static final long serialVersionUID = 8496212372310866214L;

    public RentalUpdateIsNotPossibleException(String message) {
        super(message);
    }

    public RentalUpdateIsNotPossibleException(String message, Throwable cause) {
        super(message, cause);
    }
}
