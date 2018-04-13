package com.cmpe352_group1.twitter_project.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface TrendingTopicRepository extends
        MongoRepository<TrendingTopicEntity, Long>, PagingAndSortingRepository<TrendingTopicEntity, Long> {

    List<TrendingTopicEntity> findByDateAndRegionId(String date, Long regionId);

    void deleteById(Long id);

}