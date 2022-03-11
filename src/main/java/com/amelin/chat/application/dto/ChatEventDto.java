package com.amelin.chat.application.dto;

public class ChatEventDto {
    private long chatroomId;
    private String whom;
    private EventType eventType;

    public ChatEventDto() {
    }

    public long getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(long chatroomId) {
        this.chatroomId = chatroomId;
    }

    public String getWhom() {
        return whom;
    }

    public void setWhom(String whom) {
        this.whom = whom;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
