package com.example.webapi.exceptions.bookcase;

public class BookcaseAlreadyFullException extends RuntimeException{
    public BookcaseAlreadyFullException() {
        super("This bookcase is already full.");
    }

    public BookcaseAlreadyFullException(String message) {
        super(message);
    }
}
