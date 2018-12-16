package com.cmpe451.retro.models;

import com.cmpe451.retro.data.entities.Item;


public class ItemDto {

    private String body;

    private String url;

    private Item.ItemType type;

    public ItemDto() {
    }

    public ItemDto(Item item) {
        this.body = item.getBody();
        this.url = item.getUrl();
        this.type = item.getType();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Item.ItemType getType() {
        return type;
    }

    public void setType(Item.ItemType type) {
        this.type = type;
    }
}
