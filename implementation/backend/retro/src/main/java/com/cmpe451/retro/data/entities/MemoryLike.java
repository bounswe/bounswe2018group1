package com.cmpe451.retro.data.entities;

import com.cmpe451.retro.models.LikeDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class MemoryLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private long userId;

    @NotNull
    private long memoryId;


    public MemoryLike(){}

    public MemoryLike(LikeDto likeDto){
        this.memoryId = likeDto.getMemoryId();
        this.userId = likeDto.getUserId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(long memoryId) {
        this.memoryId = memoryId;
    }
}
