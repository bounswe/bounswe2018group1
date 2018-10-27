package com.cmpe451.retro.models;

import com.cmpe451.retro.data.entities.Memory;
import com.cmpe451.retro.data.entities.Story;

import java.util.ArrayList;
import java.util.List;

public class GetMemoryResponseBody {

    private String headline;

    private String description;

    private List<StoryResponseBody> storyList;


    public GetMemoryResponseBody() {
    }

    public GetMemoryResponseBody(Memory memory) {
        this.headline = memory.getHeadline();
        this.description = memory.getDescription();
        // TO-DO: check, normally it is required to initialize this.storyList to empty list before use
        this.storyList = new ArrayList<>();
        for(Story story: memory.getStoryList()){
            this.storyList.add(new StoryResponseBody(story));
        }

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

    public List<StoryResponseBody> getStoryList() {
        return storyList;
    }

    public void setStoryList(List<StoryResponseBody> storyList) {
        this.storyList = storyList;
    }
}
