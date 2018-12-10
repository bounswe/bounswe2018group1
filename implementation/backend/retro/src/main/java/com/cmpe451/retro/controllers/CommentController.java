package com.cmpe451.retro.controllers;

import com.cmpe451.retro.data.entities.Memory;
import com.cmpe451.retro.models.ErrorResponse;
import com.cmpe451.retro.models.GetMemoryResponseBody;
import com.cmpe451.retro.services.CommentService;
import com.cmpe451.retro.services.MemoryService;
import com.cmpe451.retro.services.UserService;
import org.apache.tomcat.jni.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommentController {

    @Autowired
    private MemoryService memoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;



}
