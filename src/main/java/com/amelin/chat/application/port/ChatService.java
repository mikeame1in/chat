package com.amelin.chat.application.port;

import com.amelin.chat.application.dto.ChatMessageDto;
import com.amelin.chat.application.dto.ChatRoomDto;
import com.amelin.chat.application.dto.UserDto;

import java.util.List;
import java.util.Set;

public interface ChatService {
    Set<UserDto> registerUser(UserDto userDto);
    Set<UserDto> unregisterUser(UserDto userDto);
    ChatRoomDto createChatRoom(String who, String whom);
    ChatRoomDto getChatRoom(long chatroomId, String who);
    ChatMessageDto processMessage(ChatMessageDto message);
}
