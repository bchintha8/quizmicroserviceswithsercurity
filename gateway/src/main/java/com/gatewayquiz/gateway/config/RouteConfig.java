package com.gatewayquiz.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()

                // Auth Service (no security)
                .route("auth-service", r -> r.path("/auth/**")
                        .uri("lb://auth-service"))

                // Quiz Service
                .route("quiz-service", r -> r.path("/quiz/**")
                        .uri("lb://quiz-service"))

                // Question Service
                .route("question-service", r -> r.path("/question/**")
                        .uri("lb://question-service"))

                .build();
    }
}
