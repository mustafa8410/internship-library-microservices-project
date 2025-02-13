package com.example.webapi.exceptions.global;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException() {
        super("Some parameters provided in the request are not valid.");
    }

    public InvalidParameterException(String message) {
        super(message);
    }
}
