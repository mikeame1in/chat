package com.amelin.chat.infrastructure.dto;

import org.springframework.boot.jackson.JsonComponent;

public class JsonUserDto {
    private String username;

    public JsonUserDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
