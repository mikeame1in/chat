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

    public void addMessage(ChatMessage message) {
        messages.add(message);
    }

    public Optional<User> getWhomCompanion(User who) {
        return users.stream().filter(u -> !u.getUsername().equals(who.getUsername())).findFirst();
    }

    public boolean isUserHere(User user) {
        return users.stream().anyMatch(e -> e.getUsername().equals(user.getUsername()));
    }
}
