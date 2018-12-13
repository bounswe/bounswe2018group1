package com.cmpe451.retro.services;

import com.cmpe451.retro.data.entities.*;
import com.cmpe451.retro.data.repositories.MemoryRepository;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MemoryServiceImp implements MemoryService {

    @Autowired
    UserService userService;

    @Autowired
    MemoryRepository memoryRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public CreateMemoryResponseBody createMemory(CreateMemoryRequestBody requestBody, Long userId) {

        Optional<User> userOptional = userRepository.findById(userId);
        User user = null;

        if(!userOptional.isPresent())
            throw new RetroException("User not found.",HttpStatus.UNAUTHORIZED);
        else
            user = userOptional.get();

        Memory memory = new Memory();
        memory.setHeadline(requestBody.getHeadline());
        memory.setUserId(userId);
        memory.setUserFirstName(user.getFirstName());
        memory.setUserLastName(user.getLastName());
        memory.setUserNickname(user.getNickname());
        memory.setDateOfCreation(new Date());
        memory.setStartDateHH(requestBody.getStartDateHH());
        memory.setStartDateDD(requestBody.getStartDateDD());
        memory.setStartDateMM(requestBody.getStartDateMM());
        memory.setStartDateYYYY(requestBody.getStartDateYYYY());
        memory.setEndDateHH(requestBody.getEndDateHH());
        memory.setEndDateDD(requestBody.getEndDateDD());
        memory.setEndDateMM(requestBody.getEndDateMM());
        memory.setEndDateYYYY(requestBody.getEndDateYYYY());
        memory.setUpdatedTime(new Date());
        memory.setListOfLocations(requestBody.getListOfLocations().stream().map(Location::new).collect(Collectors.toList()));
        memory.setListOfTags(requestBody.getListOfTags().stream().map(Tag::new).collect(Collectors.toList()));
        memory.setListOfItems(requestBody.getListOfItems().stream().map(Item::new).collect(Collectors.toList()));

        memory.setListOfComments(Collections.emptyList());
        memory.setListOfMemoryLikes(Collections.emptyList());

        memory.setStartDate(convertToDate(requestBody.getStartDateDD(),requestBody.getStartDateMM(),requestBody.getStartDateYYYY()));

        if(requestBody.getEndDateYYYY()!=0){
            memory.setEndDate(convertToDate(requestBody.getEndDateDD(),requestBody.getEndDateMM(),requestBody.getEndDateYYYY()));
        }

        user.getMemoryList().add(memory);

        memory.getListOfTags().forEach(entityManager::persist);
        memory.getListOfItems().forEach(entityManager::persist);
        memory.getListOfLocations().forEach(entityManager::persist);
        //memory.getListOfComments().forEach(entityManager::persist); //TODO check
        entityManager.persist(user);
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
        return new PageImpl<>(listOfMemoryResponseBodies,pageable,listOfMemories.size());
    }

    @Override
    public Page<GetMemoryResponseBody> getAllMemoriesOfUser(Long userId,Pageable pageable) {
        List<Memory> listOfMemories = Lists.newArrayList(memoryRepository.findByUserId(userId,pageable));
        List<GetMemoryResponseBody> listOfMemoryResponseBodies = new ArrayList<>();
        for(Memory memory: listOfMemories){
            listOfMemoryResponseBodies.add(new GetMemoryResponseBody(memory));
        }
        return new PageImpl<>(listOfMemoryResponseBodies,pageable,listOfMemories.size());
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

            if(updateMemoryRequestBody.getListOfLocations() != null && !updateMemoryRequestBody.getListOfLocations().isEmpty()){
                memory.setListOfLocations(updateMemoryRequestBody.getListOfLocations().stream().map(Location::new).collect(Collectors.toList()));
                memory.getListOfLocations().forEach(entityManager::persist);

            }

            if(updateMemoryRequestBody.getListOfTags() != null && !updateMemoryRequestBody.getListOfTags().isEmpty()){
                memory.setListOfTags(updateMemoryRequestBody.getListOfTags().stream().map(Tag::new).collect(Collectors.toList()));
                memory.getListOfTags().forEach(entityManager::persist);
            }


            if(!isNullOrEmpty(updateMemoryRequestBody.getHeadline())) {
                memory.setHeadline(updateMemoryRequestBody.getHeadline());
            }

            if(updateMemoryRequestBody.getListOfItems() != null && !updateMemoryRequestBody.getListOfItems().isEmpty()){
                memory.setListOfItems(updateMemoryRequestBody.getListOfItems().stream().map(Item::new).collect(Collectors.toList()));
                memory.getListOfItems().forEach(entityManager::persist);
            }

            if(updateMemoryRequestBody.getListOfComments() != null && !updateMemoryRequestBody.getListOfComments().isEmpty()){
                memory.setListOfComments(updateMemoryRequestBody.getListOfComments().stream().map(Comment::new).collect(Collectors.toList()));
                memory.getListOfComments().forEach(entityManager::persist);
            }

            if(updateMemoryRequestBody.getListOfLikes() != null && !updateMemoryRequestBody.getListOfLikes().isEmpty()){
                memory.setListOfMemoryLikes(updateMemoryRequestBody.getListOfLikes().stream().map(MemoryLike::new).collect(Collectors.toList()));
                memory.getListOfMemoryLikes().forEach(entityManager::persist);
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

            entityManager.persist(memory);
            entityManager.flush();
        }else{
            throw new RetroException("Could not find the memory", HttpStatus.EXPECTATION_FAILED);
        }
    }

    public Page<GetMemoryResponseBody> getMemoriesWithFilter(FilterBody filterbody, Pageable pageable){

        Date startDate = convertToDate(filterbody.getStartDateDD(),filterbody.getStartDateMM(),filterbody.getStartDateYYYY());
        Date endDate = null;
        if(filterbody.getEndDateYYYY()!=0){
            endDate = convertToDate(filterbody.getEndDateDD(),filterbody.getEndDateMM(),filterbody.getEndDateYYYY());
        }

        Page<GetMemoryResponseBody> page1 = memoryRepository.findByStartDateGreaterThanEqualAndStartDateLessThanEqual(startDate,endDate,pageable);
        Page<GetMemoryResponseBody> page2 = memoryRepository.findByEndDateGreaterThanEqualAndEndDateLessThanEqual(startDate,endDate,pageable);
        Page<GetMemoryResponseBody> page3 = memoryRepository.findByStartDateGreaterThanEqualAndEndDateLessThanEqual(startDate,endDate,pageable);

        List<GetMemoryResponseBody> retList = new ArrayList<>(page1.getContent());
        if(page2.getSize()>0)
            retList.addAll(page2.getContent());
        if(page3.getSize()>0)
        retList.addAll(page3.getContent());

        int total = page1.getTotalPages() + page2.getTotalPages() + page3.getTotalPages();

        return new PageImpl<>(retList, pageable, total);
    }

    /*@Override
    public void addCommentToMemory(Long memoryId, Long userId, CreateCommentRequestBody createCommentRequestBody) {
        Optional<Memory> memoryOptional = memoryRepository.findById(memoryId);
        if(memoryOptional.isPresent()){
            Memory memory = memoryOptional.get();
            if(createCommentRequestBody != null && !createCommentRequestBody.getCommentText().equals("")){

                Comment comment = new Comment();
                comment.setCommentText(createCommentRequestBody.getCommentText());
                comment.setMemoryId(createCommentRequestBody.getMemoryId());
                comment.setUserId(createCommentRequestBody.getUserId());

                List<Comment> comments = memory.getListOfComments();
                comments.add(comment);
                memory.setListOfComments(comments.stream().map().collect(Collectors.toList()));

            }else{
                throw new RetroException("You can not post an empty comment", HttpStatus.EXPECTATION_FAILED);
            }

            //memory.getListOfComments().forEach(entityManager::persist);
            //entityManager.persist(memory);
            //entityManager.flush();

        }else{
            throw new RetroException("Memory not found.",HttpStatus.BAD_REQUEST);
        }


    }*/

    private boolean isNullOrEmpty(String s){
        return (s==null || s.isEmpty());
    }

    private boolean isZero(int i){
        return i==0;
    }

    private Date convertToDate(int d,int m, int y) {
        if(y==0){
            return null;
        }
        String day = d<10 ? "0" + d : "" + d;
        String month = m<10 ? "0" + m : "" + m;
        String year = "" + y;
        String someDate = day + "." + month + "." + year;
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");


        try {
            return sdf.parse(someDate);
        } catch (ParseException e) {
            try {
                return sdf.parse("00.00."+year);
            } catch (ParseException e1) {
                throw new RetroException("Create year is required",HttpStatus.BAD_REQUEST);
            }
        }
    }

}
