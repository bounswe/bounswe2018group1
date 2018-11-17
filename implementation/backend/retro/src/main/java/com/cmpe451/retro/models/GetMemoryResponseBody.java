package com.cmpe451.retro.models;

import com.cmpe451.retro.data.entities.Item;
import com.cmpe451.retro.data.entities.Location;
import com.cmpe451.retro.data.entities.Memory;
import com.cmpe451.retro.data.entities.Tag;

import java.util.Date;
import java.util.List;

public class GetMemoryResponseBody {

    private long userId;

    private String headline;

    private Date dateOfCreation;

    private List<Location> listOfLocations;

    private int startDateHH;

    private int startDateDD;

    private int startDateMM;

    private int startDateYYYY;


    private int endDateHH;

    private int endDateDD;

    private int endDateMM;

    private int endDateYYYY;

    private List<Tag> listOfTags;

    private List<Item> listOfItems;

    private Date updatedTime;


    public GetMemoryResponseBody() {
    }


    public GetMemoryResponseBody(Memory memory) {
        this.userId = memory.getUserId();
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
}
