package com.amelin.chat.infrastructure.dto;

import com.amelin.chat.application.dto.EventType;

public class WsChatEventDto {
    private long chatroomId;
    private String whom;
    private String whomSessionId;
    private EventType eventType;

    public WsChatEventDto() {
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

    public String getWhomSessionId() {
        return whomSessionId;
    }

    public void setWhomSessionId(String whomSessionId) {
        this.whomSessionId = whomSessionId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
