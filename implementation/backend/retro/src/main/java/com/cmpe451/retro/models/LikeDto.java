package com.cmpe451.retro.models;


import com.cmpe451.retro.data.entities.MemoryLike;

public class LikeDto {

    private long userId;

    private long memoryId;

    public LikeDto(){}

    public LikeDto(MemoryLike memoryLike){
        this.userId = memoryLike.getUserId();
        this.memoryId = memoryLike.getMemoryId();
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
