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

    private int startDateHH;

    private int startDateDD;

    private int startDateMM;

    @NotNull
    private int startDateYYYY;


    private int endDateHH;

    private int endDateDD;

    private int endDateMM;

    private int endDateYYYY;


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
