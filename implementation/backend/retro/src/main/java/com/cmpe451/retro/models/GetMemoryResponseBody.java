package com.cmpe451.retro.models;

import com.cmpe451.retro.data.entities.Memory;
import com.cmpe451.retro.data.entities.Story;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetMemoryResponseBody {

    private String headline;

    private String description;

    //Location related fields
    private String country;

    private String city;

    private String county;

    private String district;

    private LocationDto locationDto;

    //Time related fields
    private Date startDate;

    private Date endDate;


    public GetMemoryResponseBody() {
    }


    public GetMemoryResponseBody(Memory memory) {
        this.headline = memory.getHeadline();
        this.description = memory.getDescription();
        this.country = memory.getCountry();
        this.city = memory.getCity();
        this.county = memory.getCounty();
        this.district = memory.getDistrict();
        this.locationDto = memory.getLocation();
        this.startDate = memory.getStartDate();
        this.endDate = memory.getEndDate();


    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public LocationDto getLocationDto() {
        return locationDto;
    }

    public void setLocationDto(LocationDto locationDto) {
        this.locationDto = locationDto;
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
