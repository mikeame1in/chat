package com.amelin.chat.application.exception;

import com.amelin.chat.domain.model.User;

import java.util.Set;

public class ChatServiceValidatorImpl implements ChatServiceValidator {

    @Override
    public boolean isNameUnique(User newUser, Set<User> connectedUsers) {
        return connectedUsers.stream().noneMatch(user -> user.equals(newUser));
    }
}
