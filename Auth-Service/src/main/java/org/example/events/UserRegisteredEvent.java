package org.example.events;

import lombok.Builder;
import org.example.model.User;
@Builder
public record UserRegisteredEvent(
        Long userId,
        String username,
        String email,
        String firstName,
        String lastName
) {
    public static UserRegisteredEvent fromUser(User user) {
        return UserRegisteredEvent.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
