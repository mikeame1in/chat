package com.amelin.chat.infrastructure.dto;

public class WsChatMessageDto {
    private long chatroomId;
    private String who;
    private String whoSessionId;
    private String withWhom;
    private String whomSessionId;
    private String body;

    public WsChatMessageDto() {
    }

    public long getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(long chatroomId) {
        this.chatroomId = chatroomId;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWhoSessionId() {
        return whoSessionId;
    }

    public void setWhoSessionId(String whoSessionId) {
        this.whoSessionId = whoSessionId;
    }

    public String getWithWhom() {
        return withWhom;
    }

    public void setWithWhom(String withWhom) {
        this.withWhom = withWhom;
    }

    public String getWhomSessionId() {
        return whomSessionId;
    }

    public void setWhomSessionId(String whomSessionId) {
        this.whomSessionId = whomSessionId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
