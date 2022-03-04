package com.amelin.chat.application.dto;

import com.amelin.chat.domain.model.ChatRoomStatus;

public class ChatRoomDto {
    private long chatroomId;
    private String withWhom;
    private String whomSessionId;

    public ChatRoomDto() {
    }

    public long getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(long chatroomId) {
        this.chatroomId = chatroomId;
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
}