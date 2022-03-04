package com.amelin.chat.infrastructure.controller;

import com.amelin.chat.application.dto.ChatMessageDto;
import com.amelin.chat.application.dto.ChatRoomDto;
import com.amelin.chat.application.dto.UserDto;
import com.amelin.chat.application.port.ChatService;
import com.amelin.chat.infrastructure.dto.NewChatRoomClaim;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
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

    @MessageMapping("/create_chatroom")
    public void createChatroom(@RequestBody NewChatRoomClaim claim) {
        ChatRoomDto chatRoomDto = chatService.createChatRoom(claim.getWho(), claim.getWithWhom());

        chatRoomDto.setWhomSessionId(claim.getWhomSessionId());

        messagingTemplate.convertAndSendToUser(
                claim.getWhoSessionId(),
                "/queue/chatroom_list",
                chatRoomDto,
                createHeaders(claim.getWhoSessionId()));
    }

    @MessageMapping("/message")
    public void processMessage(ChatMessageDto message){
        ChatMessageDto messageDto = chatService.processMessage(message);

        messagingTemplate.convertAndSendToUser(
                messageDto.getWhom(),
                "/queue/chatroom/message",
                messageDto,
                createHeaders(messageDto.getWhom()));
    }

    @MessageMapping("/chatroom")
    public void findChatroom(@RequestBody UserDto user, String chatroomId) {

    }

    private Map<String, Object> createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create();
        headerAccessor.setSessionId(sessionId);

        return headerAccessor.getMessageHeaders();
    }
}
