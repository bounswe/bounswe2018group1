package com.cmpe451.retro.models;

public class CreateMemoryResponseBody {
    Long id;

    public CreateMemoryResponseBody() {
    }

    public CreateMemoryResponseBody(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
