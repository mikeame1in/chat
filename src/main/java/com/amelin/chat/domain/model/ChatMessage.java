package com.amelin.chat.domain.model;

import java.util.Date;

public class ChatMessage extends AbstractEntity{
    private String chatId;
    private String senderId;
    private String recipientId;
    private String senderName;
    private String recipientName;
    private String content;
    private Date timestamp;

    public ChatMessage(long id) {
        super(id);
    }
}
