package com.cmpe451.retro.models;


import com.cmpe451.retro.data.entities.Story;

import java.util.List;

public class UpdateMemoryRequestBody {
    private String headline;
    private String description;
    private List<Story> storyList; //TODO: changed it from CreateStoryRequestModel, check functionality

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

    public List<Story> getStoryList() {
        return storyList;
    }

    public void setStoryList(List<Story> storyList) {
        this.storyList = storyList;
    }

}
