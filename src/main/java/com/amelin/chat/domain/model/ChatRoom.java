package com.amelin.chat.domain.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatRoom extends AbstractEntity{
    private final List<User> users;
    private Set<ChatMessage> messages;

    public ChatRoom(long id, List<User> users) {
        super(id);
        this.users = users;
        this.messages = new HashSet<>();
    }

    public void addMessage(ChatMessage message) {
        messages.add(message);
    }
}
