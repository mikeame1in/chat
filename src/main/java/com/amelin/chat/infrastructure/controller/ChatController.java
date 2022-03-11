package com.amelin.chat.infrastructure.controller;

import com.amelin.chat.application.dto.ChatEventDto;
import com.amelin.chat.application.dto.ChatRoomDto;
import com.amelin.chat.application.dto.UserDto;
import com.amelin.chat.application.port.ChatService;
import com.amelin.chat.infrastructure.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    public void createChatroom(@RequestBody NewChatRoomClaimDto claim) {
        ChatEventDto event = chatService.createChatRoom(claim.getWho(), claim.getWithWhom());
        WsChatEventDto wsChatEventDto = transform(event, claim.getWhomSessionId());

        messagingTemplate.convertAndSendToUser(
                claim.getWhoSessionId(),
                "/queue/chatroom_list",
                wsChatEventDto,
                createHeaders(claim.getWhoSessionId()));
    }

    @MessageMapping("/chatroom")
    public void getChatroom(@RequestBody ChatRoomClaimDto claim) {
        ChatRoomDto chatRoomDto = chatService.getChatRoom(claim.getChatroomId(), claim.getWho());
        WsChatRoomDto wsChatRoomDto = transform(chatRoomDto, claim.getWhoSessionId(), claim.getWhomSessionId());

        messagingTemplate.convertAndSendToUser(
                claim.getWhoSessionId(),
                "/queue/chatroom",
                wsChatRoomDto,
                createHeaders(claim.getWhoSessionId()));
    }

    @MessageMapping("/message")
    public void processMessage(@RequestBody WsChatMessageDto message){
        ChatEventDto event = chatService.processMessage(
                message.getChatroomId(),
                message.getWho(),
                message.getBody());
        WsChatEventDto wsChatEventDto = transform(event, message.getWhoSessionId());

        messagingTemplate.convertAndSendToUser(
                message.getWhomSessionId(),
                "/queue/chatroom_list",
                wsChatEventDto,
                createHeaders(message.getWhomSessionId()));
    }

    private Map<String, Object> createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create();
        headerAccessor.setSessionId(sessionId);

        return headerAccessor.getMessageHeaders();
    }

    private WsChatRoomDto transform(ChatRoomDto dto, String whoSessionId, String whomSessionId) {
        WsChatRoomDto chatRoomDto = new WsChatRoomDto();
        chatRoomDto.setChatroomId(dto.getChatroomId());
        chatRoomDto.setWho(dto.getWho());
        chatRoomDto.setWhoSessionId(whoSessionId);
        chatRoomDto.setWithWhom(dto.getWithWhom());
        chatRoomDto.setWhomSessionId(whomSessionId);
        chatRoomDto.setMessages(dto.getMessages());

        return chatRoomDto;
    }

    private WsChatEventDto transform(ChatEventDto eventDto, String whomSessionId) {
        WsChatEventDto chatEventDto = new WsChatEventDto();
        chatEventDto.setChatroomId(eventDto.getChatroomId());
        chatEventDto.setWhom(eventDto.getWhom());
        chatEventDto.setWhomSessionId(whomSessionId);
        chatEventDto.setEventType(eventDto.getEventType());

        return chatEventDto;
    }
}
