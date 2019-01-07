package com.cmpe451.retro.models;

public class PostCommentBody {

    private String comment;

    long memoryId;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(long memoryId) {
        this.memoryId = memoryId;
    }
}
