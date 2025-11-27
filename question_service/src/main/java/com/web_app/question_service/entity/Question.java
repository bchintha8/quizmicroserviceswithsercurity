package com.web_app.question_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @NotBlank(message = "Question title cannot be empty")
    private String questionTitle;

    @NotBlank(message = "Option 1 cannot be empty")
    private String option1;

    @NotBlank(message = "Option 2 cannot be empty")
    private String option2;

    @NotBlank(message = "Option 3 cannot be empty")
    private String option3;

    @NotBlank(message = "Option 4 cannot be empty")
    private String option4;

    @NotBlank(message = "Right answer cannot be empty")
    private String rightAnswer;

    @NotBlank(message = "Category cannot be empty")
    private String category;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
