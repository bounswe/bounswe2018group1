package com.cmpe451.retro.data.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;

/**
 * Text, Image, Audio and Video classes will extend Item class
 */
@Entity
@Inheritance
public abstract class Item {

    @Id
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
