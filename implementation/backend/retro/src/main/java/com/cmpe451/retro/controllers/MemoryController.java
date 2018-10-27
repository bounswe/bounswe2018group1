package com.cmpe451.retro.controllers;

import com.cmpe451.retro.models.CreateMemoryRequestBody;
import com.cmpe451.retro.models.CreateMemoryResponseBody;
import com.cmpe451.retro.models.GetMemoryResponseBody;
import com.cmpe451.retro.services.MemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class MemoryController {

    @Autowired
    MemoryService memoryService;

    @RequestMapping(value = "/memory",method = RequestMethod.POST)
    public CreateMemoryResponseBody createMemory(@RequestBody CreateMemoryRequestBody requestBody){
        return memoryService.createMemory(requestBody);
    }

    @RequestMapping(value = "/memory",method = RequestMethod.GET)
    public GetMemoryResponseBody getMemory(Long id){ //TO-DO Required
        return memoryService.getMemory(id);
    }

}
