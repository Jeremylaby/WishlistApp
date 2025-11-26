package org.example.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String userEmail) {
        super("User with email " + userEmail + " already exists");
    }
}
