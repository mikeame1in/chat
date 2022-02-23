package com.amelin.chat.application.usecase;

import com.amelin.chat.application.dto.ChatMessageDto;
import com.amelin.chat.application.dto.ChatRoomDto;
import com.amelin.chat.application.dto.UserDto;
import com.amelin.chat.application.exception.ChatServiceValidator;
import com.amelin.chat.application.port.in.ChatService;
import com.amelin.chat.application.port.out.MessageSender;
import com.amelin.chat.domain.model.ChatMessage;
import com.amelin.chat.domain.model.ChatRoom;
import com.amelin.chat.domain.model.User;
import com.amelin.chat.domain.exception.business.ValidationException;

import java.util.*;

public class ChatServiceImpl implements ChatService {
    private final Set<User> connectedUsers;
    private final Set<ChatRoom> chatRooms;
//    private final MessageSender messageSender;
    private final ChatServiceValidator chatServiceValidator;

    public ChatServiceImpl(ChatServiceValidator chatServiceValidator) {
        this.connectedUsers =
                new HashSet<>(List.of(new User(1,"user1212"), new User(2,"user2345")));
        this.chatRooms = new HashSet<>();
        this.chatServiceValidator = chatServiceValidator;
//        this.messageSender = messageSender;
    }

    @Override
    public Set<UserDto> registerUser(UserDto userDto) {
        User user = new User(UUID.randomUUID().variant(),userDto.getUsername());

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
        Optional<User> user = connectedUsers.stream()
                .filter(e -> e.getUsername().equals(userDto.getUsername())).findFirst();

        connectedUsers.remove(user.get());

        return transform(connectedUsers);
    }

    @Override
    public ChatRoomDto startChat(UserDto firstParticipant, UserDto secondParticipant) {
        Optional<User> first = connectedUsers.stream()
                .filter(e -> !e.getUsername().equals(firstParticipant.getUsername())).findFirst();
        Optional<User> second = connectedUsers.stream()
                .filter(e -> !e.getUsername().equals(secondParticipant.getUsername())).findFirst();

        ChatRoom chatRoom = new ChatRoom(
                UUID.randomUUID().variant(),
                first.get(),
                second.get());

        chatRooms.add(chatRoom);

        return new ChatRoomDto(chatRoom.getFirst(), chatRoom.getSecond());
    }

//    @Override
//    public void processMessage(ChatMessageDto message) {
//        messageSender.sendToUser();
//    }

    private Set<UserDto> transform(Set<User> users) {
        Set<UserDto> UserDtos = new HashSet<>();

        for (User user: users) {
            UserDtos.add(new UserDto(user.getUsername()));
        }
        return UserDtos;
    }
}
