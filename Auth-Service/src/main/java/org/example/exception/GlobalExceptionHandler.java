package org.example.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public final class GlobalExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFound(UsernameNotFoundException ex) {
        return errorResponse("username", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<?> handleEmailNotFound(EmailNotFoundException ex) {
        return errorResponse("email", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
        return errorResponse("username", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameAlreadyExists.class)
    public ResponseEntity<?> handleUsernameAlreadyExists(UsernameAlreadyExists ex) {
        return errorResponse("username", ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return errorResponse("email", ex.getMessage(), HttpStatus.CONFLICT);
    }

    private ResponseEntity<?> errorResponse(String field, String message, HttpStatus status) {
        Map<String, String> error = new HashMap<>();
        error.put("errorField", field);
        error.put("errorMessage", message);
        return ResponseEntity.status(status).body(error);
    }
}
