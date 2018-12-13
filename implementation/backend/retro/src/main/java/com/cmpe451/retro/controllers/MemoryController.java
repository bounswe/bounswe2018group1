package com.cmpe451.retro.controllers;

import com.cmpe451.retro.models.*;
import com.cmpe451.retro.services.MemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowCredentials ="true", allowedHeaders ="*",
        methods = {RequestMethod.HEAD,RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@RestController
public class MemoryController {

    @Autowired
    MemoryService memoryService;

    @Autowired
    AuthenticationController authenticationController;

    @RequestMapping(value = "/memory",method = RequestMethod.POST)
    public CreateMemoryResponseBody createMemory(@RequestBody CreateMemoryRequestBody requestBody){
        long userId  = authenticationController.getUserId();
        return memoryService.createMemory(requestBody,userId);
    }

    @RequestMapping(value = "/memory",method = RequestMethod.GET)
    public GetMemoryResponseBody getMemory(Long id){ //TO-DO Required
        return memoryService.getMemory(id);
    }

    @RequestMapping(value = "/memory", method = RequestMethod.PUT)
    public void updateMemory(@RequestParam Long id, @RequestBody UpdateMemoryRequestBody updateMemoryRequestBody) {
        long userId  = authenticationController.getUserId();
        memoryService.updateMemory(id, updateMemoryRequestBody, userId);
    }

    @RequestMapping(value = "/memory/all", method = RequestMethod.GET)
    public Page<GetMemoryResponseBody> getAllMemories(Pageable pageable) { return memoryService.getAllMemories(pageable); }

    @RequestMapping(value = "/memory/user",method = RequestMethod.GET)
    public Page<GetMemoryResponseBody> getAllMemoriesOfUser(Long id, Pageable pageable){
        if(id==null){
            id = authenticationController.getUserId();
        }
        return memoryService.getAllMemoriesOfUser(id,pageable);
    }

    @RequestMapping(value = "/memory/filter",method = RequestMethod.GET)
    public Page<GetMemoryResponseBody> getMemoriesWithFilter(FilterBody filterbody,Pageable pageable){
        return memoryService.getMemoriesWithFilter(filterbody,pageable);
    }

    /*@RequestMapping(value = "/memory/comment",method = RequestMethod.PUT)
    public void addCommentToMemory(@RequestParam Long id, @RequestBody CreateCommentRequestBody createCommentRequestBody){
        long userId = authenticationController.getUserId();
        memoryService.addCommentToMemory(id, userId,createCommentRequestBody);
    }*/

}
