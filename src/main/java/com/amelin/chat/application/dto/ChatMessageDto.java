package com.amelin.chat.application.dto;

public class ChatMessageDto {
    private String who;
    private String whom;
    private String body;

    public ChatMessageDto() {
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWhom() {
        return whom;
    }

    public void setWhom(String whom) {
        this.whom = whom;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
