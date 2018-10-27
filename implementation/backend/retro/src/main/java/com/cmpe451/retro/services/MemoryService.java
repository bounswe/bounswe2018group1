package com.cmpe451.retro.services;

import com.cmpe451.retro.models.CreateMemoryRequestBody;
import com.cmpe451.retro.models.CreateMemoryResponseBody;
import com.cmpe451.retro.models.GetMemoryResponseBody;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface MemoryService {

    CreateMemoryResponseBody createMemory(CreateMemoryRequestBody requestBody);

    GetMemoryResponseBody getMemory(Long id);

    List<GetMemoryResponseBody> getAllMemories();

}
