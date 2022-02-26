package com.amelin.chat.application.dto;

import java.util.List;

public class ChatRoomDto {
    private List<UserDto> users;

    public ChatRoomDto() {
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}
