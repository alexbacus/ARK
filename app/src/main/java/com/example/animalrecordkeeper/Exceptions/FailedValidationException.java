package com.example.animalrecordkeeper.Exceptions;

public class FailedValidationException extends RuntimeException {
    public FailedValidationException(String message) {
        super(message);
    }
}
