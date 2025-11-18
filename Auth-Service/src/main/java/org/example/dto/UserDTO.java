package org.example.dto;

import lombok.Builder;
import org.example.model.Role;
import org.example.model.User;

import java.time.LocalDate;
@Builder
public record UserDTO(
    Long id,
    String email,
    String username,
    String firstname,
    String lastname,
    LocalDate birthDate,
    LocalDate nameDay,
    Role role
) {
    public static UserDTO fromUser(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .birthDate(user.getBirthDate())
                .nameDay(user.getNameDay())
                .role(user.getRole())
                .build();
    }
}
