package org.example.service;


import jakarta.transaction.Transactional;
import org.example.dto.UserDTO;
import org.example.dto.request.RegisterRequest;
import org.example.events.UserRegisteredEvent;
import org.example.exception.EmailAlreadyExistsException;
import org.example.exception.EmailNotFoundException;
import org.example.exception.UsernameAlreadyExists;
import org.example.model.Role;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserEventProducerService userEventProducerService;

    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserEventProducerService userEventProducerService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userEventProducerService = userEventProducerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public User getUserByUsername(String username) {
        return (User) loadUserByUsername(username);
    }

    @Transactional
    public UserDTO register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameAlreadyExists(request.getUsername());
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        User savedUser = userRepository.save(user);
        userEventProducerService.sendUserRegisteredEvent(UserRegisteredEvent.fromUser(savedUser));

        return UserDTO.fromUser(user);
    }

}
