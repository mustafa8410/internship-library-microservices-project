package com.example.webapi.exceptions.rent;

public class RentNotFoundException extends RuntimeException {

    public RentNotFoundException() {
    }

    public RentNotFoundException(String message) {
        super(message);
    }
}
