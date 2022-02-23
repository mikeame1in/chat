package com.amelin.chat.domain.model;

public class User extends AbstractEntity{
    private final String username;

    public User(long id, String username) {
        super(id);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
