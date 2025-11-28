package com.quiz.quiz_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Setter
@Getter
public class CreateQuizResponse {
    private Long quizId;
    private String message;
}
