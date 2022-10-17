package com.franciscothiago.bookstoremanager.exception;

public class RentalIsNotPossibleException extends RuntimeException {

    private static final long serialVersionUID = 4536638260904032821L;

    public RentalIsNotPossibleException(String message) {
        super(message);
    }

}
