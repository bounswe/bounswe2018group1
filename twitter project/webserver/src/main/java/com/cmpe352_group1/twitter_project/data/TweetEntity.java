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
    private long twitterId;

    private long trendingTopicId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(long twitterId) {
        this.twitterId = twitterId;
    }

    public long getTrendingTopicId() {
        return trendingTopicId;
    }

    public void setTrendingTopicId(long trendingTopicId) {
        this.trendingTopicId = trendingTopicId;
    }
}
