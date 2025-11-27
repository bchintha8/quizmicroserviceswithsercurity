package com.quiz.quiz_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class QuizDto {
    @Min(value = 1, message = "Number of questions must be at least 1")
    private int noOfQuestions;

    @NotBlank(message = "Category cannot be blank")
    private String category;

    @NotBlank(message = "Quiz title cannot be blank")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;
}
