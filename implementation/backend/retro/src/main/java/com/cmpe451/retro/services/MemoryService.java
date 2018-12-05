package com.cmpe451.retro.services;

import com.cmpe451.retro.models.CreateMemoryRequestBody;
import com.cmpe451.retro.models.CreateMemoryResponseBody;
import com.cmpe451.retro.models.GetMemoryResponseBody;
import com.cmpe451.retro.models.UpdateMemoryRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MemoryService {

    CreateMemoryResponseBody createMemory(CreateMemoryRequestBody requestBody, Long userId);

    GetMemoryResponseBody getMemory(Long id);

    Page<GetMemoryResponseBody> getAllMemories(Pageable pageable);

    Page<GetMemoryResponseBody> getAllMemoriesOfUser(Long userId, Pageable pageable);

    void updateMemory(Long id, UpdateMemoryRequestBody updateMemoryRequestBody, Long userId);

    void deleteMemory(Long id);
}
