package com.web_app.question_service.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {
    private boolean success;
    private String message;
    private LocalDateTime timestamp;
}
