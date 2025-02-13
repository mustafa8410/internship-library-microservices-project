package com.example.webapi.exceptions.security;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException() {
        super("Password provided is incorrect for this user.");
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }
}
