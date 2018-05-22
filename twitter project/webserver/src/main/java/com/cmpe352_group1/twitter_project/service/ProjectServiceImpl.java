package com.cmpe352_group1.twitter_project.service;

import com.cmpe352_group1.twitter_project.data.TrendingTopicEntity;
import com.cmpe352_group1.twitter_project.data.TrendingTopicRepository;
import com.cmpe352_group1.twitter_project.data.TweetEntity;
import com.cmpe352_group1.twitter_project.data.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private TrendingTopicRepository trendingTopicRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Override
    public List<TrendingTopicEntity> getTT(String date, Long regionId) {
        return trendingTopicRepository.findAllByDateAndRegionId(date, regionId);
    }

    @Override
    public List<TweetEntity> getTweets(long topicId) {
        return tweetRepository.findAllByTrendingTopicId(topicId);
    }

}
