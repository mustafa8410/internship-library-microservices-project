package com.example.webapi.exceptions.exceptionhandler;

import com.example.webapi.exceptions.security.IncorrectPasswordException;
import com.example.webapi.exceptions.security.UnauthorizedActionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SecurityExceptionHandler {
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<String> handleIncorrectPassword(Exception exception){
        String message;
        if(exception.getMessage() == null || exception.getMessage().isEmpty())
            message = "Password provided is incorrect.";
        else
            message = exception.getMessage();
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<String> handleUnauthorizedAction(Exception exception){
        String message;
        if(exception.getMessage() == null || exception.getMessage().isEmpty())
            message = "Request sender is not authorized for this action.";
        else
            message = exception.getMessage();
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

}
