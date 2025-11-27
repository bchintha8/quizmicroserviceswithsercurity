package com.web_app.question_service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Public
                        .requestMatchers("/actuator/**").permitAll()

                        // Admin-only endpoints
                        .requestMatchers("/question/add").hasRole("ADMIN")
                        .requestMatchers("/question/{id}").hasRole("ADMIN")

                        // User + Admin
                        .requestMatchers("/question/generate").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/question/questions-by-ids").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/question/calculate-score").hasAnyRole("USER", "ADMIN")

                        // Everything else must be authenticated
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
