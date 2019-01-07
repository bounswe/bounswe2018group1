package com.cmpe451.retro.data.repositories.search;

import com.cmpe451.retro.models.FilterMemoryRequest;
import com.cmpe451.retro.models.GetMemoryResponseBody;

import java.util.List;

public interface MemorySearchRepository {

    List<GetMemoryResponseBody> findMemoriesWithFilter(FilterMemoryRequest filterMemoryRequest);
}
