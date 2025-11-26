package org.example.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String userEmail) {
        super("User with email " + userEmail + " not found");
    }
}
