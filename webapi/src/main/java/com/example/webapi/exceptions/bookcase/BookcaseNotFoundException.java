package com.example.webapi.exceptions.bookcase;

public class BookcaseNotFoundException extends RuntimeException {
    public BookcaseNotFoundException() {
        super("Bookcase with given info could not be found.");
    }

    public BookcaseNotFoundException(String message) {
        super(message);
    }
}
