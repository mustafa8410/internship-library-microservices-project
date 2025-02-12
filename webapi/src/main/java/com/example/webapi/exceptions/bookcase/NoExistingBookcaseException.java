package com.example.webapi.exceptions.bookcase;

public class NoExistingBookcaseException extends RuntimeException {

    public NoExistingBookcaseException() {
    }

    public NoExistingBookcaseException(String message) {
        super(message);
    }
}
