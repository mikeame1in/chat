package com.amelin.chat.application.port;

import com.amelin.chat.application.dto.ChatEventDto;
import com.amelin.chat.application.dto.ChatRoomDto;
import com.amelin.chat.application.dto.UserDto;

import java.util.Set;

public interface ChatService {
    Set<UserDto> registerUser(UserDto userDto);
    Set<UserDto> unregisterUser(UserDto userDto);
    ChatEventDto createChatRoom(String who, String whom);
    ChatRoomDto getChatRoom(long chatroomId, String who);
    ChatEventDto processMessage(long chatRoomId, String whom, String body);
}
