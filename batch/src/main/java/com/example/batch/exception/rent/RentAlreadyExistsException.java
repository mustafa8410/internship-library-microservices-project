package com.example.batch.exception.rent;

public class RentAlreadyExistsException extends RuntimeException{
    public RentAlreadyExistsException() {
    }

    public RentAlreadyExistsException(String message) {
        super(message);
    }
}
