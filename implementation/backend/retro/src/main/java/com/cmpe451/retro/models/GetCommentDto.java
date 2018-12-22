package com.cmpe451.retro.models;

import com.cmpe451.retro.data.entities.Comment;
import com.cmpe451.retro.data.entities.User;

import java.util.Date;

public class GetCommentDto {

    private long id;

    private String text;

    private long memoryId;

    private long userId;

    private String userNickname;

    private String userProfilePicUrl;

    private String userFirstName;

    private String userLastName;

    private Date dateOfCreation;

    public GetCommentDto(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getComment();
        this.memoryId = comment.getMemory().getId();
        User user = comment.getUser();
        this.userId = user.getId();
        this.userNickname = user.getNickname();
        this.userProfilePicUrl = user.getProfilePictureUrl();
        this.userFirstName = user.getFirstName();
        this.userLastName = user.getLastName();
        this.dateOfCreation = comment.getDateOfCreation();
    }


    public GetCommentDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(long memoryId) {
        this.memoryId = memoryId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserProfilePicUrl() {
        return userProfilePicUrl;
    }

    public void setUserProfilePicUrl(String userProfilePicUrl) {
        this.userProfilePicUrl = userProfilePicUrl;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
}
