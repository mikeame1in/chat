package com.amelin.chat.domain.model;

import java.util.*;

public class ChatRoom extends AbstractEntity {
    private final List<User> users;
    private List<ChatMessage> messages;

    public ChatRoom(long id, List<User> users) {
        super(id);
        this.users = users;
        this.messages = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }
    public List<ChatMessage> getMessages() {
        return messages;
    }

    public ChatMessage processMessage(ChatMessage message) {
        messages.add(message);

        return message;
    }

    public boolean isUserHere(User user) {
        return users.stream().anyMatch(e -> e.getUsername().equals(user.getUsername()));
    }
}
