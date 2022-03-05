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
import java.util.stream.Collectors;

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
        Optional<User> whoUser = findUser(who);
        Optional<User> whomUser = findUser(whom);

        List<User> users = new ArrayList<>();
        users.add(whoUser.get());
        users.add(whomUser.get());

        ChatRoom chatRoom = new ChatRoom(UUID.randomUUID().variant(), users);

        chatRooms.add(chatRoom);

        return transform(chatRoom, whoUser.get().getUsername());
    }

    @Override
    public ChatRoomDto getChatRoom(long chatroomId, String who) {
        return transform(findChatRoomById(chatroomId).get(), who);
    }

    @Override
    public ChatMessageDto processMessage(ChatMessageDto messageDto) {
//        Optional<User> who = findUser(messageDto.getWho());
//        Optional<User> whom = findUser(messageDto.getWhom());
//
//        ChatRoom chatRoom = findChatRoomById(messageDto.getChatroomId()).get();

//        chatRoom.addMessage(transformBack(messageDto));
//        chatRooms.add(chatRoom);

        return messageDto;
    }

    private Set<UserDto> transform(Set<User> users) {
        Set<UserDto> userDtos = new HashSet<>();

        for (User user: users) {
            UserDto userDto = transform(user);
            userDtos.add(userDto);
        }

        return userDtos;
    }

    private UserDto transform(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setSessionId(user.getSessionId());

        return userDto;
    }

    private ChatMessageDto transform(ChatMessage message) {
        ChatMessageDto messageDto = new ChatMessageDto();
        messageDto.setBody(message.getBody());

        return messageDto;
    }

    private List<ChatMessageDto> transform(List<ChatMessage> messages) {
        return messages.stream().map(message -> transform(message)).collect(Collectors.toList());
    }

    private ChatRoomDto transform(ChatRoom chatRoom, String who) {
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        chatRoomDto.setChatroomId(chatRoom.getId());

        List<User> users = chatRoom.getUsers();
        String whom = users.stream().filter(u -> !u.getUsername().equals(who)).findFirst().get().getUsername();

        chatRoomDto.setWho(who);
        chatRoomDto.setWithWhom(whom);
        chatRoomDto.setMessages(transform(chatRoom.getMessages()));

        return chatRoomDto;
    }

//    private List<ChatRoomDto> transform(List<ChatRoom> chatRooms) {
//        List<ChatRoomDto> roomDtos = new ArrayList<>();
//
//        for (ChatRoom chatRoom: chatRooms) {
//            ChatRoomDto chatRoomDto = new ChatRoomDto();
//            chatRoomDto.setChatroomId(chatRoomDto.getChatroomId());
//        }
//
//        chatRooms.stream().map(e -> roomDtos.add(new ChatRoomDto()));
//    }

//    private ChatMessage transformBack(ChatMessageDto messageDto) {
//        return new ChatMessage(
//                messageDto.getWho(),
//                messageDto.getWhom(),
//                messageDto.getBody());
//    }

    private Optional<User> findUser(String username) {
        return connectedUsers.stream()
                .filter(e -> e.getUsername().equals(username)).findFirst();
    }

    private Optional<ChatRoom> findChatRoomById(long chatroomId) {
        return chatRooms.stream().filter(e -> e.getId() == chatroomId).findFirst();
    }

    private List<ChatRoom> findAllChatRoomsByUser(User user) {
        return chatRooms.stream().filter(chatRoom -> chatRoom.isUserHere(user)).collect(Collectors.toList());
    }
}
