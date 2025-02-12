package com.example.webapi.exceptions.security;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException() {
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }
}
