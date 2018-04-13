package com.cmpe352_group1.twitter_project.data;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Document
public class TrendingTopicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String date;
    private Long regionId;

    public TrendingTopicEntity() {
    }

    public TrendingTopicEntity(String name, String date, Long regionId) {
        this.name = name;
        this.date = date;
        // Reference about the region ID:
        // https://developer.twitter.com/en/docs/trends/trends-for-location/api-reference/get-trends-place
        this.regionId = regionId;
    }

    public TrendingTopicEntity(String name, String date) {
        this.name = name;
        this.date = date;
        this.regionId = 1L;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

}
