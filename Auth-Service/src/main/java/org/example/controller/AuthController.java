package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.UserDTO;
import org.example.dto.request.RegisterRequest;
import org.example.model.User;
import org.example.service.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final CustomUserDetailsService customUserDetailsService;

    public AuthController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid RegisterRequest req) {
        UserDTO dto = customUserDetailsService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkAuth(@AuthenticationPrincipal UserDetails userDetails) {
        User user = customUserDetailsService.getUserByUsername(userDetails.getUsername());
        return ResponseEntity.ok(UserDTO.fromUser(user));
    }
}
