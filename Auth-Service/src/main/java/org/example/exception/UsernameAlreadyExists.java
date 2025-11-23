package org.example.exception;

public class UsernameAlreadyExists extends RuntimeException {
    public UsernameAlreadyExists(String username) {
        super("User with user " + username + " already exists");
    }
}
