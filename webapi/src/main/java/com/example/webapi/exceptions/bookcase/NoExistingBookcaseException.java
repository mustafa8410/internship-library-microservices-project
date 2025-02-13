package com.example.webapi.exceptions.bookcase;

public class NoExistingBookcaseException extends RuntimeException {

    public NoExistingBookcaseException() {
        super("There are no bookcases in the database.");
    }

    public NoExistingBookcaseException(String message) {
        super(message);
    }
}
