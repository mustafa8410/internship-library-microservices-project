package com.example.batch.exception.bookcase;

public class BookcaseNotFoundException extends RuntimeException {
    public BookcaseNotFoundException() {
    }

    public BookcaseNotFoundException(String message) {
        super(message);
    }
}
