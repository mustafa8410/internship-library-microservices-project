package com.example.webapi.exceptions.security;

public class UnauthorizedActionException extends RuntimeException{
    public UnauthorizedActionException() {
    }

    public UnauthorizedActionException(String message) {
        super(message);
    }
}
