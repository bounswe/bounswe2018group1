package com.cmpe451.retro.services;

import com.cmpe451.retro.models.CreateMemoryRequestBody;
import com.cmpe451.retro.models.CreateMemoryResponseBody;
import com.cmpe451.retro.models.FilterMemoryRequest;
import com.cmpe451.retro.models.GetMemoryResponseBody;
import com.cmpe451.retro.models.PostCommentBody;
import com.cmpe451.retro.models.UpdateMemoryRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface MemoryService {

    CreateMemoryResponseBody createMemory(CreateMemoryRequestBody requestBody, Long userId);

    GetMemoryResponseBody getMemory(Long id);

    Page<GetMemoryResponseBody> getAllMemories(Pageable pageable);

    Page<GetMemoryResponseBody> getAllMemoriesOfUser(Long userId, Pageable pageable);

    void updateMemory(Long id, UpdateMemoryRequestBody updateMemoryRequestBody, Long userId);

    void deleteMemory(Long id, long userId);

    void likeMemory(long userId, long memoryId);

    void unlikeMemory(long userId, long memoryId);

    void comment(long userId, PostCommentBody postCommentBody);

    void deleteComment(long userId, Long commentId);

    List<GetMemoryResponseBody> filterMemory(FilterMemoryRequest requestBody);
}
