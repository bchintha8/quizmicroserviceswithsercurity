package com.quiz.quiz_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreateQuizRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String category;

    @NotNull
    private Integer numberOfQuestions;
}
