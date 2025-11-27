package com.quiz.quiz_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateQuizResponse {
    private Long quizId;
    private String message;
}
