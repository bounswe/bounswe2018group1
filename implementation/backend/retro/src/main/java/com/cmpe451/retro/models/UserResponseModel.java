package com.cmpe451.retro.models;

import com.cmpe451.retro.data.entities.Location;
import com.cmpe451.retro.data.entities.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserResponseModel {

    private long id;

    private Date dateOfCreation;

    private Date dateOfUpdate;

    private String firstName;

    private String lastName;

    private String nickname;

    private String email;

    private String birthday;

    private List<Location> listOfLocations;

    private User.Gender gender;

    private String bio;

    private String profilePictureUrl;

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
        setBirthday(user.getBirthday()); // TODO: 2018-11-13 yerine 2018-00-13 dönüyor
        this.listOfLocations = user.getListOfLocations();
        this.gender = user.getGender();
        this.bio = user.getBio();
        this.profilePictureUrl = user.getProfilePictureUrl();
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        if(birthday != null)
            this.birthday = new SimpleDateFormat("yyyy-MM-dd").format(birthday);
    }

    public List<Location> getListOfLocations() {
        return listOfLocations;
    }

    public void setListOfLocations(List<Location> listOfLocations) {
        this.listOfLocations = listOfLocations;
    }

    public User.Gender getGender() {
        return gender;
    }

    public void setGender(User.Gender gender) {
        this.gender = gender;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
}
