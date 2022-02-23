package com.amelin.chat.infrastructure.controller;

import com.amelin.chat.application.dto.ChatMessageDto;
import com.amelin.chat.application.dto.UserDto;
import com.amelin.chat.application.port.in.ChatService;
import com.amelin.chat.infrastructure.dto.JsonChatMessageDto;
import com.amelin.chat.infrastructure.dto.JsonUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/register")
    @ResponseBody
    @SendTo("/topic/users")
    public ResponseEntity<Set<JsonUserDto>> registerUser(@RequestBody JsonUserDto user){
        Set<UserDto> connectedUsers =
                chatService.registerUser(new UserDto(user.getUsername()));

        return ResponseEntity.ok(transform(connectedUsers));
    }

    @MessageMapping("/unregister")
    @ResponseBody
    @SendTo("/topic/users")
    public ResponseEntity<Set<JsonUserDto>> unregisterUser(@RequestBody JsonUserDto user){
        Set<UserDto> connectedUsers =
                chatService.unregisterUser(new UserDto(user.getUsername()));

        return ResponseEntity.ok(transform(connectedUsers));
    }

//    @MessageMapping("/message")
//    public void processMessage(JsonChatMessageDto message){
//        chatService.processMessage(new ChatMessageDto(message.getToWhom(), message.getFromWho(), message.getMessage()));
//    }

    private Set<JsonUserDto> transform(Set<UserDto> userDtos) {
        Set<JsonUserDto> jsonUserDtos = new HashSet<>();

        for (UserDto cUser: userDtos) {
            JsonUserDto jsonUserDto = new JsonUserDto();
            jsonUserDto.setUsername(cUser.getUsername());
            jsonUserDtos.add(jsonUserDto);
        }
        return jsonUserDtos;
    }
}
