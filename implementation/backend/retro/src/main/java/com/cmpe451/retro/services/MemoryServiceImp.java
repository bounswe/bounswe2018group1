package com.cmpe451.retro.services;

import com.cmpe451.retro.data.entities.Memory;
import com.cmpe451.retro.data.entities.User;
import com.cmpe451.retro.data.repositories.MemoryRepository;
import com.cmpe451.retro.models.*;
import com.google.common.collect.Lists;
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
    UserService userService;

    @Autowired
    MemoryRepository memoryRepository;

    @Autowired
    EntityManager entityManager;

    @Override
    @Transactional
    public CreateMemoryResponseBody createMemory(CreateMemoryRequestBody requestBody) {
        Memory memory = new Memory();
        User ownerUser = userService.getCurrentUser();
        memory.setUserId(ownerUser.getId());
        memory.setHeadline(requestBody.getHeadline());
        memory.setDateOfCreation(new Date());
        memory.setStartDateHH(requestBody.getStartDateHH());
        memory.setStartDateDD(requestBody.getStartDateDD());
        memory.setStartDateMM(requestBody.getStartDateMM());
        memory.setStartDateYYYY(requestBody.getStartDateYYYY());

        memory.setEndDateHH(requestBody.getEndDateHH());
        memory.setEndDateDD(requestBody.getEndDateDD());
        memory.setEndDateMM(requestBody.getEndDateMM());
        memory.setEndDateYYYY(requestBody.getEndDateYYYY());

        memory.setUpdatedTime(requestBody.getUpdatedTime());
        memory.setListOfLocations(requestBody.getListOfLocations());
        memory.setListOfTags(requestBody.getListOfTags()); //TODO: requestTagBody?
        memory.setListOfItems(requestBody.getListOfItems()); //TODO: requestItemBody?

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

            if (!isNullOrEmpty(updateMemoryRequestBody.getHeadline())) {
                memory.setHeadline(updateMemoryRequestBody.getHeadline());
            }

            if(!isZero(updateMemoryRequestBody.getStartDateHH()) &&
                    !isZero(updateMemoryRequestBody.getStartDateDD()) &&
                    !isZero(updateMemoryRequestBody.getStartDateMM()) &&
                    !isZero(updateMemoryRequestBody.getStartDateYYYY())){
                memory.setStartDateHH(updateMemoryRequestBody.getStartDateHH());
                memory.setStartDateDD(updateMemoryRequestBody.getStartDateDD());
                memory.setStartDateMM(updateMemoryRequestBody.getStartDateMM());
                memory.setStartDateYYYY(updateMemoryRequestBody.getStartDateYYYY());
            }else if(!isZero(updateMemoryRequestBody.getStartDateDD()) &&
                    !isZero(updateMemoryRequestBody.getStartDateMM()) &&
                    !isZero(updateMemoryRequestBody.getStartDateYYYY())){
                memory.setStartDateDD(updateMemoryRequestBody.getStartDateDD());
                memory.setStartDateMM(updateMemoryRequestBody.getStartDateMM());
                memory.setStartDateYYYY(updateMemoryRequestBody.getStartDateYYYY());
            }else if( !isZero(updateMemoryRequestBody.getStartDateMM()) &&
                    !isZero(updateMemoryRequestBody.getStartDateYYYY())){
                memory.setStartDateMM(updateMemoryRequestBody.getStartDateMM());
                memory.setStartDateYYYY(updateMemoryRequestBody.getStartDateYYYY());
            }else{
                memory.setStartDateYYYY(updateMemoryRequestBody.getStartDateYYYY());

            }

            if(!isZero(updateMemoryRequestBody.getEndDateHH()) &&
                    !isZero(updateMemoryRequestBody.getEndDateDD()) &&
                    !isZero(updateMemoryRequestBody.getEndDateMM()) &&
                    !isZero(updateMemoryRequestBody.getEndDateYYYY())){
                memory.setEndDateHH(updateMemoryRequestBody.getEndDateHH());
                memory.setEndDateDD(updateMemoryRequestBody.getEndDateDD());
                memory.setEndDateMM(updateMemoryRequestBody.getEndDateMM());
                memory.setEndDateYYYY(updateMemoryRequestBody.getEndDateYYYY());
            }else if(!isZero(updateMemoryRequestBody.getEndDateDD()) &&
                    !isZero(updateMemoryRequestBody.getEndDateMM()) &&
                    !isZero(updateMemoryRequestBody.getEndDateYYYY())){
                memory.setEndDateDD(updateMemoryRequestBody.getEndDateDD());
                memory.setEndDateMM(updateMemoryRequestBody.getEndDateMM());
                memory.setEndDateYYYY(updateMemoryRequestBody.getEndDateYYYY());
            }else if( !isZero(updateMemoryRequestBody.getEndDateMM()) &&
                    !isZero(updateMemoryRequestBody.getEndDateYYYY())){
                memory.setEndDateMM(updateMemoryRequestBody.getEndDateMM());
                memory.setEndDateYYYY(updateMemoryRequestBody.getEndDateYYYY());
            }else{
                memory.setEndDateYYYY(updateMemoryRequestBody.getEndDateYYYY());

            }
            /*
            if(!isNullOrEmpty(updateMemoryRequestBody.getStartDateHH()) &&
                !isNullOrEmpty(updateMemoryRequestBody.getStartDateDD()) &&
                !isNullOrEmpty(updateMemoryRequestBody.getStartDateMM()) &&
                !isNullOrEmpty(updateMemoryRequestBody.getStartDateYYYY())){
                memory.setStartDateHH(updateMemoryRequestBody.getStartDateHH());
                memory.setStartDateDD(updateMemoryRequestBody.getStartDateDD());
                memory.setStartDateMM(updateMemoryRequestBody.getStartDateMM());
                memory.setStartDateYYYY(updateMemoryRequestBody.getStartDateYYYY());
            }else if(!isNullOrEmpty(updateMemoryRequestBody.getStartDateDD()) &&
                    !isNullOrEmpty(updateMemoryRequestBody.getStartDateMM()) &&
                    !isNullOrEmpty(updateMemoryRequestBody.getStartDateYYYY())){
                memory.setStartDateDD(updateMemoryRequestBody.getStartDateDD());
                memory.setStartDateMM(updateMemoryRequestBody.getStartDateMM());
                memory.setStartDateYYYY(updateMemoryRequestBody.getStartDateYYYY());
            }else if( !isNullOrEmpty(updateMemoryRequestBody.getStartDateMM()) &&
                    !isNullOrEmpty(updateMemoryRequestBody.getStartDateYYYY())){
                memory.setStartDateMM(updateMemoryRequestBody.getStartDateMM());
                memory.setStartDateYYYY(updateMemoryRequestBody.getStartDateYYYY());
            }else{
                memory.setStartDateYYYY(updateMemoryRequestBody.getStartDateYYYY());

            }

            if(!isNullOrEmpty(updateMemoryRequestBody.getEndDateHH()) &&
                    !isNullOrEmpty(updateMemoryRequestBody.getEndDateDD()) &&
                    !isNullOrEmpty(updateMemoryRequestBody.getEndDateMM()) &&
                    !isNullOrEmpty(updateMemoryRequestBody.getEndDateYYYY())){
                memory.setEndDateHH(updateMemoryRequestBody.getEndDateHH());
                memory.setEndDateDD(updateMemoryRequestBody.getEndDateDD());
                memory.setEndDateMM(updateMemoryRequestBody.getEndDateMM());
                memory.setEndDateYYYY(updateMemoryRequestBody.getEndDateYYYY());
            }else if(!isNullOrEmpty(updateMemoryRequestBody.getEndDateDD()) &&
                    !isNullOrEmpty(updateMemoryRequestBody.getEndDateMM()) &&
                    !isNullOrEmpty(updateMemoryRequestBody.getEndDateYYYY())){
                memory.setEndDateDD(updateMemoryRequestBody.getEndDateDD());
                memory.setEndDateMM(updateMemoryRequestBody.getEndDateMM());
                memory.setEndDateYYYY(updateMemoryRequestBody.getEndDateYYYY());
            }else if( !isNullOrEmpty(updateMemoryRequestBody.getEndDateMM()) &&
                    !isNullOrEmpty(updateMemoryRequestBody.getEndDateYYYY())){
                memory.setEndDateMM(updateMemoryRequestBody.getEndDateMM());
                memory.setEndDateYYYY(updateMemoryRequestBody.getEndDateYYYY());
            }else{
                memory.setEndDateYYYY(updateMemoryRequestBody.getEndDateYYYY());

            }*/

            memory.setUpdatedTime(new Date());
            if(updateMemoryRequestBody.getListOfLocations() != null){
                memory.setListOfLocations(updateMemoryRequestBody.getListOfLocations());
            }
            if(updateMemoryRequestBody.getListOfTags() != null){
                memory.setListOfTags(updateMemoryRequestBody.getListOfTags());
            }
            if(updateMemoryRequestBody.getListOfItems() != null){
                memory.setListOfItems(updateMemoryRequestBody.getListOfItems());
            }

            //if (updateMemoryRequestBody.getStoryList() != null) {
            //    memory.setStoryList(updateMemoryRequestBody.getStoryList());
            //}

            memoryRepository.save(memory);

        }else{
            throw new RetroException("Could not find the memory", HttpStatus.EXPECTATION_FAILED);
        }
    }

    public boolean isNullOrEmpty(String s){
        return (s==null || s.isEmpty());
    }

    public boolean isZero(int i){
        return i==0;
    }

}
