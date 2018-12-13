package com.cmpe451.retro.services;

import com.cmpe451.retro.data.entities.Comment;
import com.cmpe451.retro.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MemoryService {

    CreateMemoryResponseBody createMemory(CreateMemoryRequestBody requestBody, Long userId);

    GetMemoryResponseBody getMemory(Long id);

    Page<GetMemoryResponseBody> getAllMemories(Pageable pageable);

    Page<GetMemoryResponseBody> getAllMemoriesOfUser(Long userId,Pageable pageable);

    void updateMemory(Long id, UpdateMemoryRequestBody updateMemoryRequestBody,Long userId);

    Page<GetMemoryResponseBody> getMemoriesWithFilter(FilterBody filterbody,Pageable pageable);

    //void addCommentToMemory(Long memoryId, Long userId, CreateCommentRequestBody createCommentRequestBody);
}
