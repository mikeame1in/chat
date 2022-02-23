package com.amelin.chat.application.dto;

import com.amelin.chat.domain.model.User;

public class ChatRoomDto {
    private final User first;
    private final User second;

    public ChatRoomDto(User first, User second) {
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
