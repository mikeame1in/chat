package com.amelin.chat.domain.exception.business;

public class ValidationException extends BusinessException{

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
