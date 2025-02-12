package com.example.batch.exception.mail;

public class MailNotSentException extends RuntimeException {
    public MailNotSentException() {
        super("Mail could not be sent.");
    }

    public MailNotSentException(String message) {
        super(message);
    }
}
