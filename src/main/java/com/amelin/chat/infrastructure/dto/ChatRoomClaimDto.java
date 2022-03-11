package com.amelin.chat.infrastructure.dto;

public class ChatRoomClaimDto {
    private long chatroomId;
    private String who;
    private String whoSessionId;
    private String whomSessionId;

    public ChatRoomClaimDto() {
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

    public String getWhomSessionId() {
        return whomSessionId;
    }

    public void setWhomSessionId(String whomSessionId) {
        this.whomSessionId = whomSessionId;
    }
}
