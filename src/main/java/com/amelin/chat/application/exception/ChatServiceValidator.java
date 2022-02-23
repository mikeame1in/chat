package com.amelin.chat.application.exception;

import com.amelin.chat.domain.model.User;

import java.util.Set;

public interface ChatServiceValidator {
    boolean isNameUnique(User newUser, Set<User> connectedUsers);
}
