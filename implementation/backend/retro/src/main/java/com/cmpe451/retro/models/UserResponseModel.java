package com.cmpe451.retro.models;

import com.cmpe451.retro.data.entities.User;

import java.util.Date;

public class UserResponseModel {

    private long id;

    private Date dateOfCreation;

    private Date dateOfUpdate;

    private String firstName;

    private String lastName;

    private String nickname;

    private String email;

    public UserResponseModel() {
    }

    public UserResponseModel(User user) {
        this.id = user.getId();
        this.dateOfCreation = user.getDateOfCreation();
        this.dateOfUpdate = user.getDateOfUpdate();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
