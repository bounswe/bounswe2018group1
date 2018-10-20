package com.cmpe451.retro.data.entities;

import com.cmpe451.retro.models.CreateStoryRequestModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    public Story() {
    }

    public Story(@NotNull String headline, @NotNull String description) {
        this.headline = headline;
        this.description = description;
    }

    public Story(CreateStoryRequestModel storyModel) {
        this.headline = storyModel.getHeadline();
        this.description = storyModel.getDescription();
    }

    //TODO: add image and video and audio

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
