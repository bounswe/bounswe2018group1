package com.cmpe451.retro.models;

import com.cmpe451.retro.data.entities.Story;

public class StoryResponseBody {


    private long id;

    private String headline;

    private String description;

    public StoryResponseBody() {

    }

    public StoryResponseBody(Story story) {
        this.id = story.getId();
        this.headline = story.getHeadline();
        this.description = story.getDescription();
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
}
