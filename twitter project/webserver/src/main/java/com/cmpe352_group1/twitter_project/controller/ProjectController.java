package com.cmpe352_group1.twitter_project.controller;

import com.cmpe352_group1.twitter_project.data.TrendingTopicsEntity;
import com.cmpe352_group1.twitter_project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "")
    public String homepage(){
        return "hello world!";
    }

    @RequestMapping(value = "/trendingTopics", method=RequestMethod.GET)
    public List<TrendingTopicsEntity> getTT(@RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date){
        return projectService.getTT(date);
    }
}
