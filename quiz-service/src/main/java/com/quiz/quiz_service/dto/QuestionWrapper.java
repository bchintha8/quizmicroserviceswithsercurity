package com.quiz.quiz_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionWrapper {

    private Long questionId;

    @NotBlank(message = "Question title cannot be blank")
    private String questionTitle;

    @NotBlank(message = "Option1 cannot be blank")
    private String option1;

    @NotBlank(message = "Option2 cannot be blank")
    private String option2;

    @NotBlank(message = "Option3 cannot be blank")
    private String option3;

    @NotBlank(message = "Option4 cannot be blank")
    private String option4;

    @NotBlank(message = "Category cannot be blank")
    private String category;
}
