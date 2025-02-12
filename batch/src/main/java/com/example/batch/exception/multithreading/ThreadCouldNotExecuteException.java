package com.example.batch.exception.multithreading;

public class ThreadCouldNotExecuteException extends RuntimeException {
    public ThreadCouldNotExecuteException() {
    }

    public ThreadCouldNotExecuteException(String message) {
        super(message);
    }
}
