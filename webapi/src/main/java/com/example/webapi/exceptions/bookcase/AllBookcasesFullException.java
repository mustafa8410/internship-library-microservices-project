package com.example.webapi.exceptions.bookcase;

public class AllBookcasesFullException extends RuntimeException {
    public AllBookcasesFullException() {
        super("All bookcases are full, so the action requested could not be executed fully.");
    }

    public AllBookcasesFullException(String message) {
        super(message);
    }
}
