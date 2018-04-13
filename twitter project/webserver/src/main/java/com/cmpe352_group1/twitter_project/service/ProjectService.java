package com.cmpe352_group1.twitter_project.service;

import com.cmpe352_group1.twitter_project.data.TrendingTopicEntity;
import com.cmpe352_group1.twitter_project.data.TweetEntity;

import java.util.List;

public interface ProjectService {
    List<TrendingTopicEntity> getTT(String date, Long regionId);

    List<TweetEntity> getTweets(long topicId);
}
