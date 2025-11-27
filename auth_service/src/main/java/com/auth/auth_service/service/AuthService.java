package com.auth.auth_service.service;

import com.auth.auth_service.dto.AuthRequest;
import com.auth.auth_service.dto.AuthResponse;
import com.auth.auth_service.dto.RegisterRequest;
import com.auth.auth_service.entity.User;
import com.auth.auth_service.repo.UserRepository;
import com.auth.auth_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * Register a new user (ADMIN or USER)
     */
    public String register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            return "User already exists";
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return "User Registered Successfully";
    }

    /**
     * Login and generate JWT
     */
    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        // verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        // generate JWT token
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return new AuthResponse(token, user.getRole());
    }
}
