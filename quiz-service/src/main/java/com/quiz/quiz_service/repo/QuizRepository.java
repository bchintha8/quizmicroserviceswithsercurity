package com.quiz.quiz_service.repo;

import com.quiz.quiz_service.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {
    boolean existsByQuizTitle(String quizTitle);

    List<Quiz> findByQuizTitleContainingIgnoreCase(String title);
}
