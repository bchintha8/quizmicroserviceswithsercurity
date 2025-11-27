package com.quiz.quiz_service.service;

import com.quiz.quiz_service.dto.CreateQuizRequest;
import com.quiz.quiz_service.dto.QuestionWrapper;
import com.quiz.quiz_service.dto.ResponseDto;
import com.quiz.quiz_service.entity.Quiz;
import com.quiz.quiz_service.feign.QuestionClient;
import com.quiz.quiz_service.repo.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionClient questionClient;

    /**
     * Create a quiz by requesting question IDs from question-service
     */
    public Quiz createQuiz(CreateQuizRequest request) {

        log.info("Creating quiz with title={}, category={}, count={}",
                request.getTitle(), request.getCategory(), request.getNumberOfQuestions());

        // Call question-service to get question IDs
        List<Long> questionIds = questionClient
                .getQuestionIds(request.getNumberOfQuestions(), request.getCategory());

        // Build quiz entity
        Quiz quiz = Quiz.builder()
                .quizTitle(request.getTitle())
                .questionIds(questionIds)
                .build();

        // Save to DB
        return quizRepository.save(quiz);
    }

    /**
     * Fetch full questions (without the correct answer)
     */
    public List<QuestionWrapper> getQuizQuestions(Long quizId) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found: " + quizId));

        log.info("Fetching questions for quiz {}", quizId);

        return questionClient.getQuestionsByIds(quiz.getQuestionIds());
    }

    /**
     * Calculate score by sending user responses to question-service
     */
    public Integer calculateScore(Long quizId, List<ResponseDto> responses) {

        log.info("Calculating score for quizId={} for {} responses",
                quizId, responses.size());

        return questionClient.calculateScore(responses);
    }
}
