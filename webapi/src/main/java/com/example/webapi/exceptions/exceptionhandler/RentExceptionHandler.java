package com.example.webapi.exceptions.exceptionhandler;

import com.example.webapi.exceptions.rent.RentAlreadyExistsException;
import com.example.webapi.exceptions.rent.RentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RentExceptionHandler {
    @ExceptionHandler(RentNotFoundException.class)
    public ResponseEntity<String> handleRentNotFound(Exception exception){
        String message;
        if(exception.getMessage() == null || exception.getMessage().isEmpty())
            message = "Rent not found.";
        else
            message = exception.getMessage();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RentAlreadyExistsException.class)
    public ResponseEntity<String> handleRentAlreadyExists(Exception exception){
        String message;
        if(exception.getMessage() == null || exception.getMessage().isEmpty())
            message = "Rent already exists.";
        else
            message = exception.getMessage();
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }
}
