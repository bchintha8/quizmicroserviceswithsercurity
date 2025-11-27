package com.web_app.question_service.exception;

public class NotEnoughQuestionsException extends RuntimeException {
    public NotEnoughQuestionsException(String msg) {
        super(msg);
    }
}
