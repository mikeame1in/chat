package com.amelin.chat.infrastructure.dto;

public class NewChatRoomClaimDto {
    private String who;
    private String whoSessionId;
    private String withWhom;
    private String whomSessionId;

    public NewChatRoomClaimDto() {
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
