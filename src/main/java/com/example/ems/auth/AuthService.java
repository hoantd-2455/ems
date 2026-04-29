package com.example.ems.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        // check if username already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists: " + request.getUsername());
        }
        // create new user
        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()), // hash password
                request.getRole() != null ? request.getRole() : Role.USER);
        userRepository.save(user);
        log.info("User {} registered successfully", user.getUsername());

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        // need use passwordEncoder to compare hashed password, not compare plain text
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // same error message for both cases to prevent username enumeration
            throw new IllegalArgumentException("Invalid username or password");
        }

        // generate JWT token for authenticated user
        String token = jwtService.generateToken(user);
        log.info("User {} logged in successfully", user.getUsername());
        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }
}
