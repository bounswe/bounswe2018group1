package com.cmpe352_group1.twitter_project.service;

import com.cmpe352_group1.twitter_project.data.TrendingTopicsEntity;

import java.util.Date;
import java.util.List;

public interface ProjectService {
    List<TrendingTopicsEntity> getTT(Date date);
}
