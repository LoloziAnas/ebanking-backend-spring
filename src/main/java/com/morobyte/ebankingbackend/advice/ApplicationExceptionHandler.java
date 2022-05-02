package com.morobyte.ebankingbackend.advice;

import com.morobyte.ebankingbackend.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ResourceNotFoundException.class)
    public HashMap<String, String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("errorMessage", ex.getMessage());
        return errors;
    }
}
