package com.example.webapi.exceptions.security;

public class UnauthorizedActionException extends RuntimeException{
    public UnauthorizedActionException() {
        super("Request sender is not authorized for this action.");
    }

    public UnauthorizedActionException(String message) {
        super(message);
    }
}
