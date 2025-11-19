package org.example.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Role;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminUserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @Value("${ADMIN_USERNAME}")
    private String adminUserName;

    @Value("${ADMIN_FIRST_NAME}")
    private String adminFirstName;

    @Value("${ADMIN_LAST_NAME}")
    private String adminLastName;

    @Override
    public void run(String... args) {
        // Jeśli nie ustawiono wymaganych zmiennych – pomijamy seeding
        if (adminEmail == null || adminEmail.isBlank()
                || adminPassword == null || adminPassword.isBlank()) {
            log.warn("Admin seeding skipped: ADMIN_EMAIL lub ADMIN_PASSWORD nie są ustawione.");
            return;
        }

        // Jeśli admin już istnieje – nic nie robimy
        if (userRepository.existsByUsername(adminUserName) || userRepository.existsByEmail(adminEmail)) {
            log.info("Admin user już istnieje: {}", adminEmail);
            return;
        }

        User admin = User.builder()
                .email(adminEmail)
                .password(passwordEncoder.encode(adminPassword))
                .firstName(adminFirstName)
                .lastName(adminLastName)
                .username(adminUserName)
                .role(Role.ADMIN) // zakładam, że masz enum Role.ADMIN
                .build();

        userRepository.save(admin);
        log.info("Utworzono użytkownika ADMIN: {}", adminEmail);
    }
}

