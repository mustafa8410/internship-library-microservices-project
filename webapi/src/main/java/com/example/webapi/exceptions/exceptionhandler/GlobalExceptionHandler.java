package com.example.webapi.exceptions.exceptionhandler;

import com.example.webapi.exceptions.global.InvalidParameterException;
import com.example.webapi.exceptions.global.MissingArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<String> handleInvalidParameter(InvalidParameterException exception){
        String message;
        if(exception.getMessage() == null || exception.getMessage().isEmpty())
            message = "Invalid parameters has been sent.";
        else
            message = exception.getMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingArgumentException.class)
    public ResponseEntity<String> handleMissingArgument(MissingArgumentException exception){
        String message;
        if(exception.getMessage() == null || exception.getMessage().isEmpty())
            message = "Request has been sent with missing arguments.";
        else
            message = exception.getMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
