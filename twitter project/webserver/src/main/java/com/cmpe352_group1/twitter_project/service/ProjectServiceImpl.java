package com.cmpe352_group1.twitter_project.service;

import com.cmpe352_group1.twitter_project.data.TrendingTopicsEntity;
import com.cmpe352_group1.twitter_project.data.TrendingTopicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private TrendingTopicsRepository trendingTopicsRepository;

    @Override
    public List<TrendingTopicsEntity> getTT(Date date) {
        return trendingTopicsRepository.findByDate(date);
    }
}
