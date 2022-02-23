package com.amelin.chat.domain.exception.business;

import com.amelin.chat.domain.exception.base.AppException;

public class BusinessException extends AppException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable throwable) {
        super(throwable);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
