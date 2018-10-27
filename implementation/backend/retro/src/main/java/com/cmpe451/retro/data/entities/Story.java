package com.cmpe451.retro.data.entities;

import com.cmpe451.retro.models.CreateStoryRequestModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String headline;

    @NotNull
    private String description;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date storyDate;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date dateOfCreation;

    @ManyToOne
    private Memory memory;

    private String country;

    private String city;

    private String county;

    private String district;

    @OneToOne
    private Location location;

    public Story() {
    }

    public Story(@NotNull String headline, @NotNull String description, long time) {
        this.headline = headline;
        this.description = description;
        this.storyDate = new Date(time);
        this.dateOfCreation = new Date();
    }

    public Story(CreateStoryRequestModel storyModel, Memory memory) {
        this.headline = storyModel.getHeadline();
        this.description = storyModel.getDescription();
        this.storyDate = new Date(storyModel.getTime());
        this.dateOfCreation = new Date();
        this.memory = memory;
        this.country = storyModel.getCountry();
        this.city = storyModel.getCity();
        this.country = storyModel.getCounty();
        this.district = storyModel.getDistrict();
    }

    //TODO: add image and video and audio

    public String getHeadline() {
        return this.headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
