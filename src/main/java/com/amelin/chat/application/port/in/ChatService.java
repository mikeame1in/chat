package com.amelin.chat.application.port.in;

import com.amelin.chat.application.dto.ChatMessageDto;
import com.amelin.chat.application.dto.ChatRoomDto;
import com.amelin.chat.application.dto.UserDto;

import java.util.Set;

public interface ChatService {
    Set<UserDto> registerUser(UserDto userDto);
    Set<UserDto> unregisterUser(UserDto userDto);
    ChatRoomDto startChat(UserDto firstParticipant, UserDto secondParticipant);
//    void processMessage(ChatMessageDto message);
}
