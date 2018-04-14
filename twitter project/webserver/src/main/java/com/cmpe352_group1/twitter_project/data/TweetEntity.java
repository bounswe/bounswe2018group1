package com.cmpe352_group1.twitter_project.data;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Document
public class TweetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String url;
    private long trendingTopicId;

    public TweetEntity(String url, long trendingTopicId) {
        this.url = url;
        this.trendingTopicId = trendingTopicId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTrendingTopicId() {
        return trendingTopicId;
    }

    public void setTrendingTopicId(long trendingTopicId) {
        this.trendingTopicId = trendingTopicId;
    }
}
