package com.amelin.chat.domain.model;

public abstract class AbstractEntity {
    private final long id;

    public AbstractEntity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
