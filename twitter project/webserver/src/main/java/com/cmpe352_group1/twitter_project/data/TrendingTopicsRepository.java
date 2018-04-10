package com.cmpe352_group1.twitter_project.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface TrendingTopicsRepository  extends
        MongoRepository<TrendingTopicsEntity, Long>, PagingAndSortingRepository<TrendingTopicsEntity, Long> {
    List<TrendingTopicsEntity> findByDate(Date date);
}
