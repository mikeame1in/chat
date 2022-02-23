package com.amelin.chat.application.dto;

public class UserDto {
    private final String username;

    public UserDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
