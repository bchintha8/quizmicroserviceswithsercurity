package com.gatewayquiz.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final String SECRET = "4a273c4b3fce68ebf263f8d9de4e1489f1136462f80ae66636b939bd930cba61";

    /**
     * Validate token signature + expiration.
     */
    public void validateToken(String token) throws RuntimeException {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token);  // Will throw exception if invalid
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expired", e);
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Invalid token format", e);
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Unsupported token", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Token claims string is empty", e);
        }
    }

    /**
     * Extract all claims from the token.
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
}
