package com.example.batch.exception.csv.load;

public class InvalidCsvLineException extends RuntimeException {

    public InvalidCsvLineException() {
        super("Csv line is not valid.");
    }

    public InvalidCsvLineException(String message) {
        super(message);
    }
}
