package com.cmpe451.retro.controllers;

import com.cmpe451.retro.models.CreateMemoryRequestBody;
import com.cmpe451.retro.models.CreateMemoryResponseBody;
import com.cmpe451.retro.models.GetMemoryResponseBody;
import com.cmpe451.retro.models.UpdateMemoryRequestBody;
import com.cmpe451.retro.services.MemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(value = "/memory/all", method = RequestMethod.GET)
    public List<GetMemoryResponseBody> getAllMemories() { return memoryService.getAllMemories(); }

    @RequestMapping(value = "/memory/user",method = RequestMethod.GET)
    public List<GetMemoryResponseBody> getAllMemoriesOfUser(Long id){ //TO-DO Required
        return memoryService.getAllMemoriesOfUser(id);
    }

    @RequestMapping(value = "/memory", method = RequestMethod.PUT)
    void updateMemory(Long id, @RequestBody UpdateMemoryRequestBody updateMemoryRequestBody) {
        memoryService.updateMemory(id, updateMemoryRequestBody);
    }

}
