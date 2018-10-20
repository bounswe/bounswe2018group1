package com.cmpe451.retro.services;

import com.cmpe451.retro.core.Constants;
import com.cmpe451.retro.data.entities.Memory;
import com.cmpe451.retro.data.entities.Story;
import com.cmpe451.retro.data.repositories.MemoryRepository;
import com.cmpe451.retro.data.repositories.StoryRepository;
import com.cmpe451.retro.models.CreateMemoryRequestBody;
import com.cmpe451.retro.models.CreateStoryRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemoryServiceImp implements MemoryService {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    MemoryRepository memoryRepository;

    @Autowired
    StoryRepository storyRepository;

    @Override
    public void createMemory(CreateMemoryRequestBody requestBody) {
        Memory memory = new Memory();
        memory.setDescription(requestBody.getDescription());
        memory.setHeadline(requestBody.getHeadline());
        memory.setUser(authenticationService.getUser());

        List<Story> storyList= new ArrayList<>();

        for(CreateStoryRequestModel storyRequest: requestBody.getStoryList()){
            Story story = new Story(storyRequest);
            storyList.add(story);

            storyRepository.save(story);
        }

        memory.setStoryList(storyList);
        memoryRepository.save(memory);

    }
}
