package com.cmpe451.retro.models;

import com.cmpe451.retro.data.entities.Item;

public class ItemDto {

    private String body;

    public ItemDto() {
    }

    public ItemDto(Item item) {
        this.body = item.getBody();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


}
