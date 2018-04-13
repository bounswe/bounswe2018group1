package com.cmpe352_group1.twitter_project.controller;

import com.cmpe352_group1.twitter_project.data.TrendingTopicEntity;
import com.cmpe352_group1.twitter_project.data.TweetEntity;
import com.cmpe352_group1.twitter_project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @RequestMapping(value = "/trendingTopics", method=RequestMethod.GET)
    public List<TrendingTopicEntity> getTT(@RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") String date,
                                           @RequestParam(required = false, defaultValue = "1") Long regionId) {
        return projectService.getTT(date, regionId);
    }


    @RequestMapping(value = "/tweets", method=RequestMethod.GET)
    public List<TweetEntity> getTweets(@RequestParam long topicId){
        return projectService.getTweets(topicId);
    }
}
