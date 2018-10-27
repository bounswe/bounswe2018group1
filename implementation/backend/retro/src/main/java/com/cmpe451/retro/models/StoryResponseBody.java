package com.cmpe451.retro.models;

import com.cmpe451.retro.data.entities.Location;
import com.cmpe451.retro.data.entities.Story;

import java.util.Date;

public class StoryResponseBody {


    private long id;

    private String headline;

    private String description;

    private Date storyDate;

    private Date dateOfCreation;

    private String country;

    private String city;

    private String county;

    private String district;

    private Location location;

    public StoryResponseBody() {

    }

    public StoryResponseBody(Story story) {
        this.id = story.getId();
        this.headline = story.getHeadline();
        this.description = story.getDescription();
        this.storyDate = story.getStoryDate();
        this.dateOfCreation = story.getDateOfCreation();
        this.country = story.getCountry();
        this.city = story.getCity();
        this. county = story.getCounty();
        this.district = story.getDistrict();
        this. location = story.getLocation();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getStoryDate() {
        return storyDate;
    }

    public void setStoryDate(Date storyDate) {
        this.storyDate = storyDate;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
