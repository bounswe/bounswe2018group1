package com.cmpe451.retro.models;

import com.cmpe451.retro.data.entities.Location;
import com.cmpe451.retro.data.entities.Memory;
import com.cmpe451.retro.data.entities.Story;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetMemoryResponseBody {

    private long userId;

    private String headline;

    private String text;

    private Date dateOfCreation;

    private List<Location> listOfLocations;

    private Date startDate;

    private Date endDate;

    //List<Tag> listOfTags;

    //List<MediaItem> listOfMediaItems;

    private Date updatedTime;


    public GetMemoryResponseBody() {
    }


    public GetMemoryResponseBody(Memory memory) {
        this.userId = memory.getUserId();
        this.headline = memory.getHeadline();
        this.text = memory.getText();
        this.dateOfCreation = memory.getDateOfCreation();
        this.listOfLocations = memory.getListOfLocations();
        this.startDate = memory.getStartDate();
        this.endDate = memory.getEndDate();
        //this.listOfTags = memory.getListOfTags();
        //this.listOfMediaItems = memory.getListOfMediaItems();

    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


}
