package com.quiz.quiz_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto {

    @NotNull(message = "Question ID cannot be null")
    private Long id;

    @NotBlank(message = "Response cannot be blank")
    private String response;
}

