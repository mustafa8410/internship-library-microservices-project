package com.example.webapi.exceptions.global;

public class MissingArgumentException extends RuntimeException {
    public MissingArgumentException() {
        super("There are some missing arguments in the request.");
    }

    public MissingArgumentException(String message) {
        super(message);
    }
}
