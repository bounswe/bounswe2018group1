package com.cmpe352_group1.twitter_project.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TweetRepository extends
        MongoRepository<TweetEntity, Long>, PagingAndSortingRepository<TweetEntity, Long> {

    List<TweetEntity> findAllByTrendingTopicId(long topicId);
}
