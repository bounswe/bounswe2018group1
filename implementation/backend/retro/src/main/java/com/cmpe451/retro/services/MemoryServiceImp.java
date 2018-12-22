package com.cmpe451.retro.services;

import com.cmpe451.retro.data.entities.Comment;
import com.cmpe451.retro.data.entities.Item;
import com.cmpe451.retro.data.entities.Location;
import com.cmpe451.retro.data.entities.Memory;
import com.cmpe451.retro.data.entities.Tag;
import com.cmpe451.retro.data.entities.User;
import com.cmpe451.retro.data.repositories.CommentRepository;
import com.cmpe451.retro.data.repositories.MemoryRepository;
import com.cmpe451.retro.data.repositories.UserRepository;
import com.cmpe451.retro.models.CreateMemoryRequestBody;
import com.cmpe451.retro.models.CreateMemoryResponseBody;
import com.cmpe451.retro.models.GetMemoryResponseBody;
import com.cmpe451.retro.models.PostCommentBody;
import com.cmpe451.retro.models.RetroException;
import com.cmpe451.retro.models.UpdateMemoryRequestBody;
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
import java.util.stream.Collectors;

@Service
@Transactional
public class MemoryServiceImp implements MemoryService {

    @Autowired
    UserService userService;

    @Autowired
    MemoryRepository memoryRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

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

        memory.setUpdatedTime(requestBody.getUpdatedTime());
        memory.setListOfLocations(requestBody.getListOfLocations().stream().map(Location::new).collect(Collectors.toList()));
        memory.setListOfTags(requestBody.getListOfTags().stream().map(Tag::new).collect(Collectors.toList()));
        memory.setListOfItems(requestBody.getListOfItems().stream().map(Item::new).collect(Collectors.toList()));

        user.getMemoryList().add(memory);//TODO: check

        memory.getListOfTags().forEach(entityManager::persist);
        memory.getListOfItems().forEach(entityManager::persist);
        memory.getListOfLocations().forEach(entityManager::persist);
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
            Optional<User> userOptional = userRepository.findById(memory.getUserId());
            if (userOptional.isPresent()){
                return new GetMemoryResponseBody(memory,userOptional.get());
            }
            else {
                throw new RetroException("Memory not found.", HttpStatus.NOT_FOUND);
            }
        }else{
            throw new RetroException("Memory not found.", HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public Page<GetMemoryResponseBody> getAllMemories(Pageable pageable) {
        List<Memory> listOfMemories = Lists.newArrayList(memoryRepository.findAll(pageable));
        List<GetMemoryResponseBody> listOfMemoryResponseBodies = new ArrayList<>();
        for(Memory memory: listOfMemories){
            Optional<User> userOptional = userRepository.findById(memory.getUserId());
            userOptional.ifPresent(user -> listOfMemoryResponseBodies.add(new GetMemoryResponseBody(memory, user)));
        }
        return new PageImpl<>(listOfMemoryResponseBodies,pageable,listOfMemories.size());
    }

    @Override
    public Page<GetMemoryResponseBody> getAllMemoriesOfUser(Long userId,Pageable pageable) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user;
        if(userOptional.isPresent()){
            user = userOptional.get();
        }
        else {
            throw new RetroException("User not found.",HttpStatus.NOT_FOUND);
        }
        List<Memory> listOfMemories = Lists.newArrayList(memoryRepository.findByUserId(userId,pageable));
        List<GetMemoryResponseBody> listOfMemoryResponseBodies = new ArrayList<>();

        for(Memory memory: listOfMemories){
            listOfMemoryResponseBodies.add(new GetMemoryResponseBody(memory, user));
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

    @Override
    public void deleteMemory(Long id, long userId) {
        Optional<Memory> memoryOptional = memoryRepository.findById(id);
        if(!memoryOptional.isPresent())
            return;
        Memory memory = memoryOptional.get();
        if(memory.getUserId()==userId)
            memoryRepository.deleteById(id);
        else
            throw new RetroException("You can only delete your memories",HttpStatus.UNAUTHORIZED);
    }

    @Override
    public void likeMemory(long userId, long memoryId) {
        Optional<Memory> memoryOptional = memoryRepository.findById(memoryId);
        if(!memoryOptional.isPresent())
            throw new RetroException("Memory not found", HttpStatus.NOT_FOUND);
        Memory memory = memoryOptional.get();
        memory.getLikedUsers().add(userId);
        memoryRepository.save(memory);
    }

    @Override
    public void unlikeMemory(long userId, long memoryId) {
        Optional<Memory> memoryOptional = memoryRepository.findById(memoryId);
        if(!memoryOptional.isPresent())
            throw new RetroException("Memory not found", HttpStatus.NOT_FOUND);
        Memory memory = memoryOptional.get();
        memory.getLikedUsers().remove(userId);
        memoryRepository.save(memory);
    }

    public void comment(long userId, PostCommentBody postCommentBody){
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Memory> memoryOptional = memoryRepository.findById(postCommentBody.getMemoryId());
        if(!userOptional.isPresent() || !memoryOptional.isPresent()){
            throw new RetroException("Memory not found.",HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        Memory memory = memoryOptional.get();
        Comment comment = new Comment();
        comment.setMemory(memory);
        comment.setUser(user);
        comment.setComment(postCommentBody.getComment());
        memory.getComments().add(comment);
        entityManager.persist(comment);
        entityManager.persist(memory);
        entityManager.flush();
    }

    public void deleteComment(long userId, Long commentId){
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if(!commentOptional.isPresent())
            throw new RetroException("Comment not found.",HttpStatus.NOT_FOUND);
        Comment comment = commentOptional.get();
        if(comment.getUser().getId() != userId)
            throw new RetroException("You can only delete your comments.",HttpStatus.UNAUTHORIZED);
        Memory memory = comment.getMemory();
        memory.getComments().remove(comment);
        entityManager.remove(comment);
        entityManager.persist(memory);
        entityManager.flush();
    }

    public boolean isNullOrEmpty(String s){
        return (s==null || s.isEmpty());
    }

    public boolean isZero(int i){
        return i==0;
    }

}
