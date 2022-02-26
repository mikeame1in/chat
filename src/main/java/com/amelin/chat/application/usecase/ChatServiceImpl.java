package com.amelin.chat.application.usecase;

import com.amelin.chat.application.dto.ChatMessageDto;
import com.amelin.chat.application.dto.ChatRoomDto;
import com.amelin.chat.application.dto.UserDto;
import com.amelin.chat.application.exception.ChatServiceValidator;
import com.amelin.chat.application.port.ChatService;
import com.amelin.chat.domain.model.ChatMessage;
import com.amelin.chat.domain.model.ChatRoom;
import com.amelin.chat.domain.model.User;
import com.amelin.chat.domain.exception.business.ValidationException;

import java.util.*;

public class ChatServiceImpl implements ChatService {
    private final Set<User> connectedUsers;
    private final Set<ChatRoom> chatRooms;
    private final ChatServiceValidator chatServiceValidator;

    public ChatServiceImpl(ChatServiceValidator chatServiceValidator) {
        this.connectedUsers =
                new HashSet<>(List.of(
                        new User(1,"user1212", ""),
                        new User(2,"user2345", "")));
        this.chatRooms = new HashSet<>();
        this.chatServiceValidator = chatServiceValidator;
    }

    @Override
    public Set<UserDto> registerUser(UserDto userDto) {
        User user = new User(UUID.randomUUID().variant(),userDto.getUsername(), userDto.getSessionId());

        if (chatServiceValidator.isNameUnique(user, connectedUsers)) {
            connectedUsers.add(user);

            return transform(connectedUsers);
        }
        else {
            throw new ValidationException("Username is not unique");
        }
    }

    @Override
    public Set<UserDto> unregisterUser(UserDto userDto) {
        String username = userDto.getUsername();
        Optional<User> user = findUser(username);

        connectedUsers.remove(user.get());

        return transform(connectedUsers);
    }

    @Override
    public ChatRoomDto createChatRoom(String who, String whom) {
        return null;
    }

    @Override
    public ChatMessageDto processMessage(ChatMessageDto messageDto) {
//        ChatMessage message = transformBack(messageDto);
//
//        Optional<ChatRoom> chatRoom = findChatRoom();
//        Optional<User> who = findUser(message.getWho());
//        Optional<User> whom = findUser(message.getWhom());
//
//        ChatRoom room = null;
//
//        if (chatRoom.equals(Optional.empty())) {
//            room = new ChatRoom(
//                            UUID.randomUUID().variant(),
//                            List.of(who.get(), whom.get()));
//            chatRooms.add(room);
//        } else {
//            room = chatRoom.get();
//        }
//
//        room.addMessage(message);
        Optional<User> who = findUser(messageDto.getWho());
        Optional<User> whom = findUser(messageDto.getWhom());

        messageDto.setWho(who.get().getSessionId());
        messageDto.setWhom(whom.get().getSessionId());

        return messageDto;
    }

    private Set<UserDto> transform(Set<User> users) {
        Set<UserDto> userDtos = new HashSet<>();

        for (User user: users) {
            UserDto userDto = new UserDto();
            userDto.setUsername(user.getUsername());
            userDto.setSessionId(user.getSessionId());
            userDtos.add(userDto);
        }

        return userDtos;
    }

    private ChatMessage transformBack(ChatMessageDto messageDto) {
        return new ChatMessage(
                messageDto.getWho(),
                messageDto.getWhom(),
                messageDto.getBody());
    }

    private Optional<User> findUser(String username) {
        return connectedUsers.stream()
                .filter(e -> e.getUsername().equals(username)).findFirst();
    }

    private Optional<ChatRoom> findChatRoom() {
        return Optional.empty();
    }
}
