package com.cmpe451.retro.models;


import com.cmpe451.retro.data.entities.Item;
import com.cmpe451.retro.data.entities.Location;
import com.cmpe451.retro.data.entities.Story;
import com.cmpe451.retro.data.entities.Tag;

import java.util.Date;
import java.util.List;

public class UpdateMemoryRequestBody {

    private String headline;

    private String text;

    private Date dateOfCreation;

    private List<Location> listOfLocations;

    private String startDateHH;

    private String startDateDD;

    private String startDateMM;

    private String startDateYYYY;

    private String endDateHH;

    private String endDateDD;

    private String endDateMM;

    private String endDateYYYY;

    private List<Tag> listOfTags;

    private List<Item> listOfItems;

    private Date updatedTime;

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
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

    public String getStartDateHH() {
        return startDateHH;
    }

    public void setStartDateHH(String startDateHH) {
        this.startDateHH = startDateHH;
    }

    public String getStartDateDD() {
        return startDateDD;
    }

    public void setStartDateDD(String startDateDD) {
        this.startDateDD = startDateDD;
    }

    public String getStartDateMM() {
        return startDateMM;
    }

    public void setStartDateMM(String startDateMM) {
        this.startDateMM = startDateMM;
    }

    public String getStartDateYYYY() {
        return startDateYYYY;
    }

    public void setStartDateYYYY(String startDateYYYY) {
        this.startDateYYYY = startDateYYYY;
    }

    public String getEndDateHH() {
        return endDateHH;
    }

    public void setEndDateHH(String endDateHH) {
        this.endDateHH = endDateHH;
    }

    public String getEndDateDD() {
        return endDateDD;
    }

    public void setEndDateDD(String endDateDD) {
        this.endDateDD = endDateDD;
    }

    public String getEndDateMM() {
        return endDateMM;
    }

    public void setEndDateMM(String endDateMM) {
        this.endDateMM = endDateMM;
    }

    public String getEndDateYYYY() {
        return endDateYYYY;
    }

    public void setEndDateYYYY(String endDateYYYY) {
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
