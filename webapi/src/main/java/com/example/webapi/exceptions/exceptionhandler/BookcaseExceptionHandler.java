package com.example.webapi.exceptions.exceptionhandler;

import com.example.webapi.exceptions.bookcase.AllBookcasesFullException;
import com.example.webapi.exceptions.bookcase.BookcaseAlreadyFullException;
import com.example.webapi.exceptions.bookcase.BookcaseNotFoundException;
import com.example.webapi.exceptions.bookcase.NoExistingBookcaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookcaseExceptionHandler {

    @ExceptionHandler(AllBookcasesFullException.class)
    public ResponseEntity<String> handleAllBookcasesFull(AllBookcasesFullException exception){
        String message;
        if(exception.getMessage() == null || exception.getMessage().isEmpty())
            message = "All bookcases are full.";
        else
            message = exception.getMessage();
        return new ResponseEntity<>(message, HttpStatus.INSUFFICIENT_STORAGE);
    }

    @ExceptionHandler(BookcaseAlreadyFullException.class)
    public ResponseEntity<String> handleBookcaseAlreadyFull(BookcaseAlreadyFullException exception){
        String message;
        if(exception.getMessage() == null || exception.getMessage().isEmpty())
            message = "This bookcase is already full.";
        else
            message = exception.getMessage();
        return new ResponseEntity<>(message, HttpStatus.INSUFFICIENT_STORAGE);    }

    @ExceptionHandler(BookcaseNotFoundException.class)
    public ResponseEntity<String> handleBookcaseNotFound(BookcaseNotFoundException exception){
        String message;
        if(exception.getMessage() == null || exception.getMessage().isEmpty())
            message = "Bookcase is not found.";
        else
            message = exception.getMessage();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoExistingBookcaseException.class)
    public ResponseEntity<String> handleNoExistingBookcase(NoExistingBookcaseException exception){
        String message;
        if(exception.getMessage() == null || exception.getMessage().isEmpty())
            message = "There are no existing bookcase in the database.";
        else
            message = exception.getMessage();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
