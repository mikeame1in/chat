package com.amelin.chat.domain.model;

public class User extends AbstractEntity{
    private final String username;
    private final String sessionId;

    public User(long id, String username, String sessionId) {
        super(id);
        this.username = username;
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public String getSessionId() {
        return sessionId;
    }
}
