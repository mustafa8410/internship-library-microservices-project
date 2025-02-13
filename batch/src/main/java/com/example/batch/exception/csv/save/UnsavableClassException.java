package com.example.batch.exception.csv.save;

public class UnsavableClassException extends RuntimeException {

    public UnsavableClassException() {
    }

    public UnsavableClassException(String message) {
        super(message);
    }
}
