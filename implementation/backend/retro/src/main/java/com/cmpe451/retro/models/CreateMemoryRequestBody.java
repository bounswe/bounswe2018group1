package com.cmpe451.retro.models;

import java.util.List;

public class CreateMemoryRequestBody {

    private String headline;
    private String description;
    private List<CreateStoryRequestModel> storyList;

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

    public List<CreateStoryRequestModel> getStoryList() {
        return storyList;
    }

    public void setStoryList(List<CreateStoryRequestModel> storyList) {
        this.storyList = storyList;
    }
}
