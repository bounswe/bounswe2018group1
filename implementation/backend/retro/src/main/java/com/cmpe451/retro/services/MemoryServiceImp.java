package com.cmpe451.retro.services;

import com.cmpe451.retro.data.entities.Location;
import com.cmpe451.retro.data.entities.Memory;
import com.cmpe451.retro.data.entities.Story;
import com.cmpe451.retro.data.entities.User;
import com.cmpe451.retro.data.repositories.MemoryRepository;
import com.cmpe451.retro.data.repositories.StoryRepository;
import com.cmpe451.retro.data.repositories.UserRepository;
import com.cmpe451.retro.models.*;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public CreateMemoryResponseBody createMemory(CreateMemoryRequestBody requestBody, Long userId) {
        Memory memory = new Memory();
        memory.setDescription(requestBody.getDescription());
        memory.setHeadline(requestBody.getHeadline());
        memory.setUserId(userId);
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

        Optional<User> user = userRepository.findById(userId);

        memory.setStoryList(storyList);
        user.get().getMemoryList().add(memory);
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
    public Page<GetMemoryResponseBody> getAllMemories(Pageable pageable) {
        List<Memory> listOfMemories = Lists.newArrayList(memoryRepository.findAll(pageable));
        List<GetMemoryResponseBody> listOfMemoryResponseBodies = new ArrayList<>();
        for(Memory memory: listOfMemories){
            listOfMemoryResponseBodies.add(new GetMemoryResponseBody(memory));
        }
        return new PageImpl<>(listOfMemoryResponseBodies);
    }

    @Override
    public Page<GetMemoryResponseBody> getAllMemoriesOfUser(Long userId,Pageable pageable) {
        return memoryRepository.findByUserId(userId,pageable);
    }

    @Override
    @Transactional
    public void updateMemory(Long id, UpdateMemoryRequestBody updateMemoryRequestBody,Long userId) {
        Optional<Memory> memoryOptional = memoryRepository.findById(id);
        if(memoryOptional.isPresent()){
            Memory memory = memoryOptional.get();
            if(memory.getUserId() != userId){
                throw new RetroException("You can not update a memory that you have not created.",HttpStatus.UNAUTHORIZED);
            }

            if (updateMemoryRequestBody.getDescription() != null && !updateMemoryRequestBody.getDescription().equals("")) {
                memory.setDescription(updateMemoryRequestBody.getDescription());
            }

            if (updateMemoryRequestBody.getHeadline() != null && !updateMemoryRequestBody.getHeadline().equals("")) {
                memory.setHeadline(updateMemoryRequestBody.getHeadline());
            }

            if (updateMemoryRequestBody.getStoryList() != null) {
                memory.setStoryList(updateMemoryRequestBody.getStoryList());
            }
            memory.getStoryList().forEach(entityManager::persist);
            memory.getStoryList().stream().map(Story::getLocation).forEach(entityManager::persist);

            entityManager.persist(memory);
            entityManager.flush();
        }else{
            throw new RetroException("Could not find the memory", HttpStatus.EXPECTATION_FAILED);
        }
    }


}
