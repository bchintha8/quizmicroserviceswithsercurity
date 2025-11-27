package com.quiz.quiz_service.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="quizzes")
@Builder
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long quizId;
    @NotBlank(message = "Quiz title cannot be blank")
    @Size(min = 3,max = 100,message = "Title must be between 3 and 100 characters")
    private String quizTitle;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "quiz_question_ids",            joinColumns = @JoinColumn(name = "quiz_id")
    )
    @Column(name = "question_id")
    private List<Long> questionIds;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
