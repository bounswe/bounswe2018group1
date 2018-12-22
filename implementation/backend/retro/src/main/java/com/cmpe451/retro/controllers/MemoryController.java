package com.cmpe451.retro.controllers;

import com.cmpe451.retro.models.CreateMemoryRequestBody;
import com.cmpe451.retro.models.CreateMemoryResponseBody;
import com.cmpe451.retro.models.GetMemoryResponseBody;
import com.cmpe451.retro.models.PostCommentBody;
import com.cmpe451.retro.models.UpdateMemoryRequestBody;
import com.cmpe451.retro.services.MemoryService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(notes = "Valid item types: TEXT,PHOTO,VIDEO,AUDIO.",
            value = "")
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

    @RequestMapping(value = "/memory", method = RequestMethod.DELETE)
    public void deleteMemory(long id){
        long userId = authenticationController.getUserId();
        memoryService.deleteMemory(id,userId);
    }

    @RequestMapping(value = "/memory/like",method = RequestMethod.POST)
    public void likeMemory(@RequestParam long memoryId){
        long userId  = authenticationController.getUserId();
        memoryService.likeMemory(userId,memoryId);
    }

    @RequestMapping(value = "/memory/unlike",method = RequestMethod.POST)
    public void unlikeMemory(@RequestParam long memoryId){
        long userId  = authenticationController.getUserId();
        memoryService.unlikeMemory(userId,memoryId);
    }

    @RequestMapping(value = "/memory/comment",method = RequestMethod.POST)
    public void comment(@RequestBody PostCommentBody postCommentBody){
        long userId  = authenticationController.getUserId();
        memoryService.comment(userId,postCommentBody);
    }

    @RequestMapping(value = "/memory/comment",method = RequestMethod.DELETE)
    public void deleteComment(@RequestParam Long commentId){
        long userId  = authenticationController.getUserId();
        memoryService.deleteComment(userId,commentId);
    }



}
