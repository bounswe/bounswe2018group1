package com.cmpe451.retro.data.entities;


import com.cmpe451.retro.models.CommentDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private long memoryId;

    @NotNull
    private long userId;

    private String userNickname;

    private String userFirstName;

    private String userLastName;

    @Lob
    @Column(nullable = false)
    @NotNull
    private String commentText;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfCreation;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfUpdate;

    /*@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memory_id", nullable = false)
    private Memory memory;*/


    public Comment(){}

    public Comment(CommentDto commentDto) {

        this.commentText = commentDto.getCommentText();
        this.dateOfCreation = commentDto.getDateOfCreation();
        this.dateOfUpdate = commentDto.getDateOfUpdate();
        this.memoryId = commentDto.getMemoryId();
        this.userId = commentDto.getUserId();
        this.userFirstName = commentDto.getUserFirstName();
        this.userLastName = commentDto.getUserLastName();
        this.userNickname = commentDto.getUserNickname();

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Date getDateOfUpdate() {
        return dateOfUpdate;
    }

    public void setDateOfUpdate(Date dateOfUpdate) {
        this.dateOfUpdate = dateOfUpdate;
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

}
