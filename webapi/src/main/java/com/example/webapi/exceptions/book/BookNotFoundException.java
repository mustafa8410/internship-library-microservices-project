package com.example.webapi.exceptions.book;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException() {
        super("A book with given information could not be found.");
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}
