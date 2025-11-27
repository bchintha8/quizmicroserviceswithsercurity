package com.quiz.quiz_service.controller;

import com.quiz.quiz_service.dto.CreateQuizRequest;
import com.quiz.quiz_service.dto.CreateQuizResponse;
import com.quiz.quiz_service.dto.QuestionWrapper;
import com.quiz.quiz_service.dto.ResponseDto;
import com.quiz.quiz_service.entity.Quiz;
import com.quiz.quiz_service.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class Qcontroller {

    private final QuizService quizService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createQuiz(@Valid @RequestBody CreateQuizRequest request) {

        log.info("Request to create quiz with title: {}", request.getTitle());

        Quiz createdQuiz = quizService.createQuiz(request);

        return ResponseEntity
                .status(201)
                .body(new CreateQuizResponse(
                        createdQuiz.getQuizId(),
                        "Quiz created successfully"
                ));
    }

    @GetMapping("/{quizId}/questions")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Long quizId) {

        log.info("Request to get questions for quiz: {}", quizId);

        List<QuestionWrapper> questions = quizService.getQuizQuestions(quizId);
        return ResponseEntity.ok(questions);
    }

    @PostMapping("/{quizId}/submit")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Integer> submitQuiz(
            @PathVariable Long quizId,
            @Valid @RequestBody List<ResponseDto> responses) {

        log.info("Submitting quiz {} with {} responses", quizId, responses.size());

        Integer score = quizService.calculateScore(quizId, responses);
        return ResponseEntity.ok(score);
    }
}
