package com.amelin.chat.domain.model;

public class ChatRoom extends AbstractEntity{
    private final User first;
    private final User second;

    public ChatRoom(long id, User first, User second) {
        super(id);
        this.first = first;
        this.second = second;
    }

    public User getFirst() {
        return first;
    }

    public User getSecond() {
        return second;
    }
}
