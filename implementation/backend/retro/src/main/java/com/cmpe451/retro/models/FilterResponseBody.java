package com.cmpe451.retro.models;

import java.util.List;

public class FilterResponseBody {
    List<GetMemoryResponseBody> content;

    public List<GetMemoryResponseBody> getContent() {
        return content;
    }

    public void setContent(List<GetMemoryResponseBody> content) {
        this.content = content;
    }
}
