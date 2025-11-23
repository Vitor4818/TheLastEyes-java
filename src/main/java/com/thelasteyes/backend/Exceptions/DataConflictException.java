package com.thelasteyes.backend.Exceptions;

public class DataConflictException extends RuntimeException {

    public DataConflictException(String message) {
        super(message);
    }

}