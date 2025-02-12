package com.example.webapi.exceptions.bookcase;

public class AllBookcasesFullException extends RuntimeException {
    public AllBookcasesFullException() {
    }

    public AllBookcasesFullException(String message) {
        super(message);
    }
}
