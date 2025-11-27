package com.web_app.question_service.service;

import com.web_app.question_service.dto.QuestionWrapper;
import com.web_app.question_service.dto.ResponseDto;
import com.web_app.question_service.entity.Question;
import com.web_app.question_service.repo.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;

    /**
     * Add a new question
     */
    public Question addQuestion(Question question) {
        log.info("Adding question: {}", question.getQuestionTitle());
        return questionRepository.save(question);
    }

    /**
     * Get all questions (admin/internal use)
     */
    public List<Question> getAllQuestions() {
        log.info("Fetching all questions");
        return questionRepository.findAll();
    }

    /**
     * Delete a question by ID
     */
    public void deleteQuestion(Long id) {
        log.info("Deleting question with id {}", id);

        if (!questionRepository.existsById(id)) {
            throw new RuntimeException("Question ID not found: " + id);
        }

        questionRepository.deleteById(id);
    }

    /**
     * Generate random question IDs for quiz-service
     */
    public List<Long> generateRandomQuestions(int count, String category) {

        log.info("Generating {} random questions for category: {}", count, category);

        List<Question> questions = questionRepository.findByCategory(category);

        if (questions.size() < count) {
            throw new RuntimeException("Not enough questions available.");
        }

        Collections.shuffle(questions);

        return questions.stream()
                .limit(count)
                .map(Question::getQuestionId)
                .collect(Collectors.toList());
    }

    /**
     * Return questions without correct answers (for quiz display)
     */
    public List<QuestionWrapper> getQuestionsForQuiz(List<Long> ids) {

        log.info("Fetching questions for quiz: {}", ids);

        List<Question> questions = questionRepository.findByQuestionIdIn(ids);

        return questions.stream().map(q ->
                QuestionWrapper.builder()
                        .questionId(q.getQuestionId())
                        .questionTitle(q.getQuestionTitle())
                        .option1(q.getOption1())
                        .option2(q.getOption2())
                        .option3(q.getOption3())
                        .option4(q.getOption4())
                        .category(q.getCategory())
                        .build()
        ).collect(Collectors.toList());
    }

    /**
     * Calculate score based on user answers
     */
    public int calculateScore(List<ResponseDto> responses) {

        log.info("Calculating score for {} responses", responses.size());

        int score = 0;

        for (ResponseDto response : responses) {
            Optional<Question> questionOpt = questionRepository.findById(response.getQuestionId());

            if (questionOpt.isEmpty()) continue;

            Question question = questionOpt.get();

            if (question.getRightAnswer().equalsIgnoreCase(response.getResponse())) {
                score++;
            }
        }

        return score;
    }
}
