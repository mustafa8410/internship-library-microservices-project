package com.example.webapi.exceptions.global;

public class MissingArgumentException extends RuntimeException {
    public MissingArgumentException() {
    }

    public MissingArgumentException(String message) {
        super(message);
    }
}
