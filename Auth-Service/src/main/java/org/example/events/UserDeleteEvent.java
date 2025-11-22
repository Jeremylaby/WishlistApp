package org.example.events;

public record UserDeleteEvent(
        Long userId,
        String username,
        String email,
        String reason,
        String firstName,
        String lastName
) {
}
