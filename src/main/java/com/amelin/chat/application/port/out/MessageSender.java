package com.amelin.chat.application.port.out;

import com.amelin.chat.domain.model.ChatMessage;

public interface MessageSender {
    void sendToUser(ChatMessage message);
}
