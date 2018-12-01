package com.cmpe451.retro.data.entities;

import com.cmpe451.retro.models.TagDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String text;

    public Tag() {
    }

    public Tag(TagDto tagDto) {
        this.text = tagDto.getText();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
