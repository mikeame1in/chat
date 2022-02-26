package com.amelin.chat.application.port;

import com.amelin.chat.application.dto.ChatMessageDto;
import com.amelin.chat.application.dto.ChatRoomDto;
import com.amelin.chat.application.dto.UserDto;

import java.util.Set;

public interface ChatService {
    Set<UserDto> registerUser(UserDto userDto);
    Set<UserDto> unregisterUser(UserDto userDto);
    ChatRoomDto createChatRoom(String who, String whom);
    ChatMessageDto processMessage(ChatMessageDto message);
}
