package com.amelin.chat.application.dto;

public class ChatMessageDto {
    private String who;
    private String whoSessionId;
    private String whom;
    private String whomSessionId;
    private long chatroomId;
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

    public long getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(long chatroomId) {
        this.chatroomId = chatroomId;
    }

    public String getWhoSessionId() {
        return whoSessionId;
    }

    public void setWhoSessionId(String whoSessionId) {
        this.whoSessionId = whoSessionId;
    }

    public String getWhomSessionId() {
        return whomSessionId;
    }

    public void setWhomSessionId(String whomSessionId) {
        this.whomSessionId = whomSessionId;
    }
}
