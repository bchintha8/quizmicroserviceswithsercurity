package com.quiz.quiz_service.feign;

import com.quiz.quiz_service.dto.QuestionWrapper;
import com.quiz.quiz_service.dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "question-service")
public interface QuestionClient {

    /**
     * Generate random question IDs for a quiz
     */
    @GetMapping("/question/generate")
    List<Long> getQuestionIds(
            @RequestParam("count") int count,
            @RequestParam("category") String category
    );

    /**
     * Get all question details for given IDs (correct answer hidden)
     */
    @PostMapping("/question/questions-by-ids")
    List<QuestionWrapper> getQuestionsByIds(
            @RequestBody List<Long> questionIds
    );

    /**
     * Calculate score based on user responses
     */
    @PostMapping("/question/calculate-score")
    Integer calculateScore(
            @RequestBody List<ResponseDto> responses
    );
}
