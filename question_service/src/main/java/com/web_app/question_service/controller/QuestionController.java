package com.web_app.question_service.controller;

import com.web_app.question_service.dto.QuestionWrapper;
import com.web_app.question_service.dto.ResponseDto;
import com.web_app.question_service.entity.Question;
import com.web_app.question_service.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    /**
     * Add question (ADMIN)
     */
    @PostMapping("/add")
    @PreAuthorize("ADMIN")
    public ResponseEntity<Question> addQuestion(@Valid @RequestBody Question question) {
        Question saved = questionService.addQuestion(question);
        return ResponseEntity.status(201).body(saved);
    }

    @PreAuthorize("ADMIN")
    @GetMapping("/all")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @PreAuthorize("ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/generate")
    public ResponseEntity<List<Long>> generateQuestions(
            @RequestParam int count,
            @RequestParam String category) {

        return ResponseEntity.ok(
                questionService.generateRandomQuestions(count, category)
        );
    }


    @PostMapping("/questions-by-ids")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsByIds(
            @RequestBody List<Long> ids) {

        return ResponseEntity.ok(
                questionService.getQuestionsForQuiz(ids)
        );
    }


    @PostMapping("/calculate-score")
    public ResponseEntity<Integer> calculateScore(
            @Valid @RequestBody List<ResponseDto> responses) {

        return ResponseEntity.ok(
                questionService.calculateScore(responses)
        );
    }
}
