package com.cmpe451.retro.models;

import java.util.Date;
import java.util.List;

public class CreateMemoryRequestBody {

    private String headline;

    private Date dateOfCreation;

    private List<LocationDto> listOfLocations;

    private int startDateHH;

    private int startDateDD;

    private int startDateMM;

    private int startDateYYYY;

    private int endDateHH;

    private int endDateDD;

    private int endDateMM;

    private int endDateYYYY;

    private List<TagDto> listOfTags;

    private List<ItemDto> listOfItems;

    private Date updatedTime;

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

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public void setListOfLocations(List<LocationDto> listOfLocations) {
        this.listOfLocations = listOfLocations;
    }

    public List<TagDto> getListOfTags() {
        return listOfTags;
    }

    public void setListOfTags(List<TagDto> listOfTags) {
        this.listOfTags = listOfTags;
    }

    public List<ItemDto> getListOfItems() {
        return listOfItems;
    }

    public void setListOfItems(List<ItemDto> listOfItems) {
        this.listOfItems = listOfItems;
    }

    public List<LocationDto> getListOfLocations() {
        return listOfLocations;
    }
}
