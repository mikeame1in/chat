package com.amelin.chat.domain.exception.base;

public abstract class AppException extends RuntimeException{

    public AppException(String message) {
        super(message);
    }

    public AppException(Throwable throwable) {
        super(throwable);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
