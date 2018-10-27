package com.cmpe451.retro.services;

import com.cmpe451.retro.data.entities.Location;
import com.cmpe451.retro.data.entities.Memory;
import com.cmpe451.retro.data.entities.Story;
import com.cmpe451.retro.data.entities.User;
import com.cmpe451.retro.data.repositories.MemoryRepository;
import com.cmpe451.retro.data.repositories.StoryRepository;
import com.cmpe451.retro.models.*;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MemoryServiceImp implements MemoryService {

    @Autowired
    UserService userService;

    @Autowired
    MemoryRepository memoryRepository;

    @Autowired
    StoryRepository storyRepository;

    @Autowired
    EntityManager entityManager;

    @Override
    @Transactional
    public CreateMemoryResponseBody createMemory(CreateMemoryRequestBody requestBody) {
        Memory memory = new Memory();
        memory.setDescription(requestBody.getDescription());
        memory.setHeadline(requestBody.getHeadline());
        User ownerUser = userService.getCurrentUser();
        memory.setUserId(ownerUser.getId());
        memory.setDateOfCreation(new Date());

        List<Story> storyList= new ArrayList<>();

        for(CreateStoryRequestModel storyRequest: requestBody.getStoryList()){
            Story story = new Story(storyRequest, memory);
            Location location = new Location(storyRequest.getLocationDto());
            story.setLocation(location);
            storyList.add(story);

            entityManager.persist(location);
            entityManager.persist(story);
        }

        memory.setStoryList(storyList);
        ownerUser.getMemoryList().add(memory); //TODO: check
        entityManager.persist(memory);
        entityManager.flush();

        return new CreateMemoryResponseBody(memory.getId());
    }

    @Override
    public GetMemoryResponseBody getMemory(Long id){
        Optional<Memory> memoryOptional = memoryRepository.findById(id);
        if(memoryOptional.isPresent()){
            Memory memory = memoryOptional.get();
            return new GetMemoryResponseBody(memory);

        }else{
            throw new RetroException("Memory not found.", HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public List<GetMemoryResponseBody> getAllMemories() {
        List<Memory> listOfMemories = Lists.newArrayList(memoryRepository.findAll());
        List<GetMemoryResponseBody> listOfMemoryResponseBodies = new ArrayList<>();
        for(Memory memory: listOfMemories){
            listOfMemoryResponseBodies.add(new GetMemoryResponseBody(memory));
        }
        return listOfMemoryResponseBodies;
    }

    @Override
    public List<GetMemoryResponseBody> getAllMemoriesOfUser(Long userId) {
        //TODO check how removeIf works
        List<Memory> listOfMemories = Lists.newArrayList(memoryRepository.findAll());
        listOfMemories.removeIf(m -> m.getUserId() != userId);
        List<GetMemoryResponseBody> listOfMemoryResponseBodies = new ArrayList<>();
        for(Memory memory: listOfMemories){
            listOfMemoryResponseBodies.add(new GetMemoryResponseBody(memory));
        }
        return listOfMemoryResponseBodies;
    }

    @Override
    public void updateMemory(Long id, UpdateMemoryRequestBody updateMemoryRequestBody) {
        Optional<Memory> memoryOptional = memoryRepository.findById(id);
        if(memoryOptional.isPresent()){
            Memory memory = memoryOptional.get();
            if (updateMemoryRequestBody.getDescription() != null && !updateMemoryRequestBody.getDescription().equals("")) {
                memory.setDescription(updateMemoryRequestBody.getDescription());
            }

            if (updateMemoryRequestBody.getHeadline() != null && !updateMemoryRequestBody.getHeadline().equals("")) {
                memory.setHeadline(updateMemoryRequestBody.getHeadline());
            }

            if (updateMemoryRequestBody.getStoryList() != null) {
                memory.setStoryList(updateMemoryRequestBody.getStoryList());
            }

            memoryRepository.save(memory);

        }else{
            throw new RetroException("Could not find the memory", HttpStatus.EXPECTATION_FAILED);
        }
    }


}
