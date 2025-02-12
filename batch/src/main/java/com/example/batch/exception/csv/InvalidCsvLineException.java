package com.example.batch.exception.csv;

public class InvalidCsvLineException extends RuntimeException {

    public InvalidCsvLineException() {
    }

    public InvalidCsvLineException(String message) {
        super(message);
    }
}
