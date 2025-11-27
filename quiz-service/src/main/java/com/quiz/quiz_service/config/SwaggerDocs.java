package com.quiz.quiz_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerDocs {
    @Bean
    public OpenAPI SwaggerDocs(){

        return new OpenAPI().info(new Info().title("quiz service api").version("1.0").description("API Documentation for quiz service"));
    }
}
