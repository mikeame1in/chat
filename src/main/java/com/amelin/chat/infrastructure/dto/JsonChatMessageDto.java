package com.amelin.chat.infrastructure.dto;

public class JsonChatMessageDto {
    private final String toWhom;
    private final String fromWho;
    private final String message;

    public JsonChatMessageDto(final String toWhom, final String fromWho, final String message){
        this.toWhom  = toWhom;
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
