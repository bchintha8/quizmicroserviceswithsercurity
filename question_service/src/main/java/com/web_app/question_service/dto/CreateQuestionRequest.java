package com.web_app.question_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateQuestionRequest {

    @NotBlank(message = "Question title cannot be blank")
    private String questionTitle;

    @NotBlank(message = "Option 1 cannot be blank")
    private String option1;

    @NotBlank(message = "Option 2 cannot be blank")
    private String option2;

    @NotBlank(message = "Option 3 cannot be blank")
    private String option3;

    @NotBlank(message = "Option 4 cannot be blank")
    private String option4;

    @NotBlank(message = "Right answer cannot be blank")
    private String rightAnswer;

    @NotBlank(message = "Category cannot be blank")
    private String category;
}
