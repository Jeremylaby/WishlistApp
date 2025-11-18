package org.example.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public final class GlobalExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFound(UsernameNotFoundException ex) {
        return errorResponse("username", ex.getMessage(), HttpStatus.NOT_FOUND);
    }





    private ResponseEntity<?> errorResponse(String field, String message, HttpStatus status) {
        Map<String, String> error = new HashMap<>();
        error.put("errorField", field);
        error.put("errorMessage", message);
        return ResponseEntity.status(status).body(error);
    }

}

