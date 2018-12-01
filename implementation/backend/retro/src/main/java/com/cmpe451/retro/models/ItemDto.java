package com.cmpe451.retro.models;

import com.cmpe451.retro.data.entities.Item;

public class ItemDto {

    private String body;

    private String url;

    public ItemDto() {
    }

    public ItemDto(Item item) {
        this.body = item.getBody();
        this.url = item.getUrl();
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
}
