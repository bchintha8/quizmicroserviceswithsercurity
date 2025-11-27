package com.auth.auth_service.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    // Use environment variable in production
    private static final String SECRET_KEY = "4a273c4b3fce68ebf263f8d9de4e1489f1136462f80ae66636b939bd930cba61";

    // Token expiration → 10 hours
    private static final long EXPIRATION = 1000 * 60 * 60 * 10;

    /**
     * Generate JWT with username + role
     */
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();
    }
}
