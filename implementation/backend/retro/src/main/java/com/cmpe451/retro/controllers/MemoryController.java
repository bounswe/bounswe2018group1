package com.cmpe451.retro.controllers;

import com.cmpe451.retro.models.CreateMemoryRequestBody;
import com.cmpe451.retro.services.MemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemoryController {

    @Autowired
    MemoryService memoryService;

    @RequestMapping(value = "/memory",method = RequestMethod.POST)
    public void createMemory(@RequestBody CreateMemoryRequestBody requestBody){
        memoryService.createMemory(requestBody);
    }



}
