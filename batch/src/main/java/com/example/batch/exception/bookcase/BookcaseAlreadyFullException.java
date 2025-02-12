package com.example.batch.exception.bookcase;

public class BookcaseAlreadyFullException extends RuntimeException{
    public BookcaseAlreadyFullException() {
    }

    public BookcaseAlreadyFullException(String message) {
        super(message);
    }
}
