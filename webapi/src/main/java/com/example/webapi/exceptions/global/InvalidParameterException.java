package com.example.webapi.exceptions.global;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException() {
    }

    public InvalidParameterException(String message) {
        super(message);
    }
}
