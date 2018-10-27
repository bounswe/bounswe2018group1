package com.cmpe451.retro.services;

import com.cmpe451.retro.data.entities.Memory;
import com.cmpe451.retro.data.entities.Story;
import com.cmpe451.retro.data.repositories.MemoryRepository;
import com.cmpe451.retro.data.repositories.StoryRepository;
import com.cmpe451.retro.models.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    AuthenticationService authenticationService;

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
        memory.setUser(authenticationService.getUser());
        memory.setDateOfCreation(new Date());

        List<Story> storyList= new ArrayList<>();

        for(CreateStoryRequestModel storyRequest: requestBody.getStoryList()){
            Story story = new Story(storyRequest, memory);
            storyList.add(story);

            entityManager.persist(story);
        }

        memory.setStoryList(storyList);
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
}
