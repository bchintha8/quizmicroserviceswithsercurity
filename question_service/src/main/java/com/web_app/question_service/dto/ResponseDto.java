package com.web_app.question_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto {

    @NotNull(message = "Question ID cannot be null")
    private Long questionId;

    @NotBlank(message = "Response cannot be empty")
    private String response;
}
