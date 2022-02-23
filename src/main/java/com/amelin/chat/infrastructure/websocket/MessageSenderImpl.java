package com.amelin.chat.infrastructure.websocket;

import com.amelin.chat.application.port.out.MessageSender;
import com.amelin.chat.domain.model.ChatMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class MessageSenderImpl implements MessageSender {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public MessageSenderImpl(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void sendToUser(ChatMessage message) {
//        simpMessagingTemplate.convertAndSendToUser(message., "/msg", message);
    }
}
