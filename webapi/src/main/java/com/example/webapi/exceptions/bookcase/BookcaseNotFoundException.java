package com.example.webapi.exceptions.bookcase;

public class BookcaseNotFoundException extends RuntimeException {
    public BookcaseNotFoundException() {
    }

    public BookcaseNotFoundException(String message) {
        super(message);
    }
}
