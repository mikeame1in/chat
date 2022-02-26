package com.amelin.chat.infrastructure.dto;

public class ChatRoomCreateMessage {
    private String who;
    private String withWhom;

    public ChatRoomCreateMessage() {
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWithWhom() {
        return withWhom;
    }

    public void setWithWhom(String withWhom) {
        this.withWhom = withWhom;
    }
}
