package com.amelin.chat.domain.model;

import java.time.LocalTime;

public class ChatMessage{
    private final String who;
    private final String whom;
    private final String body;

    public ChatMessage(String who, String whom, String body) {
        this.who = who;
        this.whom = whom;
        this.body = body;
    }

    public String getWho() {
        return who;
    }

    public String getWhom() {
        return whom;
    }

    public String getBody() {
        return body;
    }
}
