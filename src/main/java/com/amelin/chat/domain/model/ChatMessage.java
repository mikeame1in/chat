package com.amelin.chat.domain.model;

import java.time.LocalTime;

public class ChatMessage{
    private final String body;

    public ChatMessage(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}
