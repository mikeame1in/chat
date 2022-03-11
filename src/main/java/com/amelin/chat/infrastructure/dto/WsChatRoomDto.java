package com.amelin.chat.infrastructure.dto;

import com.amelin.chat.application.dto.ChatMessageDto;

import java.util.List;

public class WsChatRoomDto {
    private long chatroomId;
    private String who;
    private String whoSessionId;
    private String withWhom;
    private String whomSessionId;
    private List<ChatMessageDto> messages;

    public WsChatRoomDto() {
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

    public List<ChatMessageDto> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessageDto> messages) {
        this.messages = messages;
    }
}
