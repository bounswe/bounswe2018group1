package com.cmpe451.retro.models;

import com.cmpe451.retro.data.entities.Item;
import com.cmpe451.retro.data.entities.Location;
import com.cmpe451.retro.data.entities.Memory;
import com.cmpe451.retro.data.entities.Tag;
import com.cmpe451.retro.data.entities.User;
import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GetMemoryResponseBody {

    private long id;

    private long userId;

    private String userNickname;

    private String userFirstName;

    private String userLastName;

    private String userProfilePicUrl;

    private String headline;

    private Date dateOfCreation;

    private List<Location> listOfLocations;

    private int startDateHH;

    private int startDateDD;

    private int startDateMM;

    private int startDateYYYY;

    private int yearRange;

    private int endDateHH;

    private int endDateDD;

    private int endDateMM;

    private int endDateYYYY;

    private List<Tag> listOfTags;

    private List<Item> listOfItems;

    private Date updatedTime;

    private Set<Long> likedUsers;

    private List<GetCommentDto> comments;

    public GetMemoryResponseBody() {
    }


    public GetMemoryResponseBody(Memory memory, User user) {
        this.userId = user.getId();
        this.userNickname = user.getNickname();
        this.userFirstName = user.getFirstName();
        this.userLastName = user.getLastName();
        this.userProfilePicUrl = user.getProfilePictureUrl();
        this.headline = memory.getHeadline();
        this.dateOfCreation = memory.getDateOfCreation();
        this.startDateHH = memory.getStartDateHH();
        this.startDateDD = memory.getStartDateDD();
        this.startDateMM = memory.getStartDateMM();
        this.startDateYYYY = memory.getStartDateYYYY();
        this.endDateHH = memory.getEndDateHH();
        this.endDateDD = memory.getEndDateDD();
        this.endDateMM = memory.getEndDateMM();
        this.endDateYYYY = memory.getEndDateYYYY();
        this.updatedTime = memory.getUpdatedTime();
        this.listOfLocations = memory.getListOfLocations();
        this.listOfTags = memory.getListOfTags();
        this.listOfItems = memory.getListOfItems();
        this.id = memory.getId();
        this.likedUsers = memory.getLikedUsers();
        this.comments = memory.getComments().stream().map(GetCommentDto::new).collect(Collectors.toList());
        this.yearRange = memory.getYearRange();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public List<Location> getListOfLocations() {
        return listOfLocations;
    }

    public void setListOfLocations(List<Location> listOfLocations) {
        this.listOfLocations = listOfLocations;
    }

    public int getStartDateHH() {
        return startDateHH;
    }

    public void setStartDateHH(int startDateHH) {
        this.startDateHH = startDateHH;
    }

    public int getStartDateDD() {
        return startDateDD;
    }

    public void setStartDateDD(int startDateDD) {
        this.startDateDD = startDateDD;
    }

    public int getStartDateMM() {
        return startDateMM;
    }

    public void setStartDateMM(int startDateMM) {
        this.startDateMM = startDateMM;
    }

    public int getStartDateYYYY() {
        return startDateYYYY;
    }

    public void setStartDateYYYY(int startDateYYYY) {
        this.startDateYYYY = startDateYYYY;
    }

    public int getEndDateHH() {
        return endDateHH;
    }

    public void setEndDateHH(int endDateHH) {
        this.endDateHH = endDateHH;
    }

    public int getEndDateDD() {
        return endDateDD;
    }

    public void setEndDateDD(int endDateDD) {
        this.endDateDD = endDateDD;
    }

    public int getEndDateMM() {
        return endDateMM;
    }

    public void setEndDateMM(int endDateMM) {
        this.endDateMM = endDateMM;
    }

    public int getEndDateYYYY() {
        return endDateYYYY;
    }

    public void setEndDateYYYY(int endDateYYYY) {
        this.endDateYYYY = endDateYYYY;
    }

    public List<Tag> getListOfTags() {
        return listOfTags;
    }

    public void setListOfTags(List<Tag> listOfTags) {
        this.listOfTags = listOfTags;
    }

    public List<Item> getListOfItems() {
        return listOfItems;
    }

    public void setListOfItems(List<Item> listOfItems) {
        this.listOfItems = listOfItems;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
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

    public String getUserProfilePicUrl() {
        return userProfilePicUrl;
    }

    public void setUserProfilePicUrl(String userProfilePicUrl) {
        this.userProfilePicUrl = userProfilePicUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Long> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(Set<Long> likedUsers) {
        this.likedUsers = likedUsers;
    }

    public List<GetCommentDto> getComments() {
        return comments;
    }

    public void setComments(List<GetCommentDto> comments) {
        this.comments = comments;
    }

    public int getYearRange() {
        return yearRange;
    }

    public void setYearRange(int yearRange) {
        this.yearRange = yearRange;
    }
}
