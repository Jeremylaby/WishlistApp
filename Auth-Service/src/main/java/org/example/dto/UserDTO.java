package org.example.dto;

import java.time.LocalDate;
import lombok.Builder;
import org.example.model.Role;
import org.example.model.User;

@Builder
public record UserDTO(
        Long id,
        String email,
        String username,
        String firstName,
        String lastName,
        LocalDate birthDate,
        LocalDate nameDay,
        Role role) {
    public static UserDTO fromUser(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .nameDay(user.getNameDay())
                .role(user.getRole())
                .build();
    }
}
