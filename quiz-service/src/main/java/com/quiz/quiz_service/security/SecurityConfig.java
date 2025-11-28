package com.quiz.quiz_service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    private static final String[] SWAGGER_WHITELIST = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/v3/api-docs",
            "/swagger-resources/**",
            "/webjars/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Allow Swagger UI without authentication
                        .requestMatchers(SWAGGER_WHITELIST).permitAll()

                        // Actuator (optional)
                        .requestMatchers("/actuator/**").permitAll()

                        // Public auth endpoints (if any)
                        .requestMatchers("/api/auth/**").permitAll()

                        // Admin-only access
                        .requestMatchers("/quiz/create").hasRole("ADMIN")

                        // Admin & User access
                        .requestMatchers("/quiz/*/questions").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/quiz/*/submit").hasAnyRole("ADMIN", "USER")

                        // All other API calls require authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // JWT filter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
