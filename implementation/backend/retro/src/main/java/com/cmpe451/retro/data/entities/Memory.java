package com.cmpe451.retro.data.entities;

import com.cmpe451.retro.models.CreateMemoryRequestBody;
import com.cmpe451.retro.models.LocationDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Memory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private long userId;

    @NotNull
    private String headline;

    @NotNull
    private Date dateOfCreation;

//    @NotNull
//    @Temporal(TemporalType.DATE)
//    private Date startDate;
//
//    @NotNull
//    @Temporal(TemporalType.DATE)
//    private Date endDate;

    private String startDateHH;

    private String startDateDD;

    private String startDateMM;

    @NotNull
    private String startDateYYYY;


    private String endDateHH;

    private String endDateDD;

    private String endDateMM;

    private String endDateYYYY;


    @NotNull
    @Temporal(TemporalType.DATE)
    private Date updatedTime;

    @NotNull
    @OneToMany(targetEntity=Location.class)
    private List<Location> listOfLocations;

    @OneToMany(targetEntity=Tag.class)
    private List<Tag> listOfTags;

    @NotNull
    @OneToMany(targetEntity=Item.class)
    private List<Item> listOfItems;


    public Memory(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public List<Location> getListOfLocations() {
        return listOfLocations;
    }

    public void setListOfLocations(List<Location> listOfLocations) {
        this.listOfLocations = listOfLocations;
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

}
