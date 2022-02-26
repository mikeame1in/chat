package com.amelin.chat.infrastructure.controller;

import com.amelin.chat.application.dto.ChatMessageDto;
import com.amelin.chat.application.dto.ChatRoomDto;
import com.amelin.chat.application.dto.UserDto;
import com.amelin.chat.application.port.ChatService;
import com.amelin.chat.infrastructure.dto.ChatRoomCreateMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/register")
    @ResponseBody
    @SendTo("/topic/users")
    public ResponseEntity<Set<UserDto>> registerUser(@RequestBody UserDto user,
                                                     SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        user.setSessionId(sessionId);

        Set<UserDto> connectedUsers =
                chatService.registerUser(user);

        return ResponseEntity.ok(connectedUsers);
    }

    @MessageMapping("/unregister")
    @ResponseBody
    @SendTo("/topic/users")
    public ResponseEntity<Set<UserDto>> unregisterUser(@RequestBody UserDto user) {
        Set<UserDto> connectedUsers =
                chatService.unregisterUser(user);

        return ResponseEntity.ok(connectedUsers);
    }

    @MessageMapping("/message")
    public void handleMessage(ChatMessageDto message){
        ChatMessageDto messageDto = chatService.processMessage(message);

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(messageDto.getWhom());
        headerAccessor.setLeaveMutable(true);

        messagingTemplate.convertAndSendToUser(
                messageDto.getWhom(),
                "/queue/chatroom",
                messageDto,
                headerAccessor.getMessageHeaders());
    }
}
