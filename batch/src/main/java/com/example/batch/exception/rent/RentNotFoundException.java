package com.example.batch.exception.rent;

public class RentNotFoundException extends RuntimeException {

    public RentNotFoundException() {
    }

    public RentNotFoundException(String message) {
        super(message);
    }
}
