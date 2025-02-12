package com.example.webapi.exceptions.exceptionhandler;

import com.example.webapi.exceptions.user.UserAlreadyExistsException;
import com.example.webapi.exceptions.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException exception){
        String message;
        if(exception.getMessage() == null || exception.getMessage().isEmpty())
            message = "A user with one of the special credentials already exists.";
        else
            message = exception.getMessage();
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException exception){
        String message;
        if(exception.getMessage() == null || exception.getMessage().isEmpty())
            message = "User is not found.";
        else
            message = exception.getMessage();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

}
