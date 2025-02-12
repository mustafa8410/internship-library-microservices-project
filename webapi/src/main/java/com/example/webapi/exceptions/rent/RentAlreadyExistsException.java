package com.example.webapi.exceptions.rent;

public class RentAlreadyExistsException extends RuntimeException{
    public RentAlreadyExistsException() {
    }

    public RentAlreadyExistsException(String message) {
        super(message);
    }
}
