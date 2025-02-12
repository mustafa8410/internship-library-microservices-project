package com.example.batch.exception.csv;

public class InvalidCsvFileException extends RuntimeException {
    public InvalidCsvFileException() {
    }

    public InvalidCsvFileException(String message) {
        super(message);
    }
}
