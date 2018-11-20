package com.cmpe451.retro.data.entities;

import com.cmpe451.retro.models.ItemDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;

/**
 * Text, Image, Audio and Video classes will extend Item class
 */
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String body;

    public Item(long id, String body) {
        this.id = id;
        this.body = body;
    }

    public Item() {
    }

    public Item(ItemDto itemDto) {
        this.body = itemDto.getBody();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
