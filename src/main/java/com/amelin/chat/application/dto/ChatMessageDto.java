package com.amelin.chat.application.dto;

public class ChatMessageDto {
    private final String toWhom;
    private final String fromWho;
    private final String message;

    public ChatMessageDto(String toWhom, String fromWho, String message) {
        this.toWhom = toWhom;
        this.fromWho = fromWho;
        this.message = message;
    }

    public String getToWhom() {
        return toWhom;
    }

    public String getFromWho() {
        return fromWho;
    }

    public String getMessage() {
        return message;
    }
}
