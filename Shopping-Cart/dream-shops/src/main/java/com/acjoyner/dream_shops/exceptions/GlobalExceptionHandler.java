package com.acjoyner.dream_shops.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex){
        String message = "You don't have permission to perform this action";
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }
}
