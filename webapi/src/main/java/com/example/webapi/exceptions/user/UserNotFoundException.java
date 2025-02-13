package com.example.webapi.exceptions.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User with given data could not be found.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
