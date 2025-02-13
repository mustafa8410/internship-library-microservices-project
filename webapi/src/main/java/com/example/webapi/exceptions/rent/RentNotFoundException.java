package com.example.webapi.exceptions.rent;

public class RentNotFoundException extends RuntimeException {

    public RentNotFoundException() {
        super("Any rent with given data could not be found.");
    }

    public RentNotFoundException(String message) {
        super(message);
    }
}
