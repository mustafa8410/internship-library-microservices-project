package com.example.webapi.exceptions.rent;

public class RentAlreadyExistsException extends RuntimeException{
    public RentAlreadyExistsException() {
        super("Either the book that is requested to be rented is already rented to someone else, or this exact " +
                "rent already exists, so it can't be recreated.");
    }

    public RentAlreadyExistsException(String message) {
        super(message);
    }
}
