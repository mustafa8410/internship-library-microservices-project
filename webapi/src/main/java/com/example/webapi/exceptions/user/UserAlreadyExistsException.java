package com.example.webapi.exceptions.user;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("A user with one of the unique credentials that are provided already exists.");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
