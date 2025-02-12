package com.example.webapi.exceptions.bookcase;

public class BookcaseAlreadyFullException extends RuntimeException{
    public BookcaseAlreadyFullException() {
    }

    public BookcaseAlreadyFullException(String message) {
        super(message);
    }
}
