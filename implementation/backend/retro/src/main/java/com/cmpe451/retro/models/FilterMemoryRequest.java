package com.cmpe451.retro.models;

import java.util.List;

public class FilterMemoryRequest {

    private Integer startDateHH;

    private Integer startDateDD;

    private Integer startDateMM;

    private Integer startDateYYYY;

    private Integer endDateHH;

    private Integer endDateDD;

    private Integer endDateMM;

    private Integer endDateYYYY;

    private List<TagDto> listOfTags;

    private LocationDto location;

    private String text;

    public Integer getStartDateHH() {
        return startDateHH;
    }

    public void setStartDateHH(Integer startDateHH) {
        this.startDateHH = startDateHH;
    }

    public Integer getStartDateDD() {
        return startDateDD;
    }

    public void setStartDateDD(Integer startDateDD) {
        this.startDateDD = startDateDD;
    }

    public Integer getStartDateMM() {
        return startDateMM;
    }

    public void setStartDateMM(Integer startDateMM) {
        this.startDateMM = startDateMM;
    }

    public Integer getEndDateHH() {
        return endDateHH;
    }

    public void setEndDateHH(Integer endDateHH) {
        this.endDateHH = endDateHH;
    }

    public Integer getEndDateDD() {
        return endDateDD;
    }

    public void setEndDateDD(Integer endDateDD) {
        this.endDateDD = endDateDD;
    }

    public Integer getEndDateMM() {
        return endDateMM;
    }

    public void setEndDateMM(Integer endDateMM) {
        this.endDateMM = endDateMM;
    }

    public Integer getEndDateYYYY() {
        return endDateYYYY;
    }

    public void setEndDateYYYY(Integer endDateYYYY) {
        this.endDateYYYY = endDateYYYY;
    }

    public List<TagDto> getListOfTags() {
        return listOfTags;
    }

    public void setListOfTags(List<TagDto> listOfTags) {
        this.listOfTags = listOfTags;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getStartDateYYYY() {
        return startDateYYYY;
    }

    public void setStartDateYYYY(Integer startDateYYYY) {
        this.startDateYYYY = startDateYYYY;
    }
}