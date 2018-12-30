package com.annotation.retroAnnotation.models;


public class CreateAnnotationResponseBody {

    private Long id;

    public CreateAnnotationResponseBody(){}

    public CreateAnnotationResponseBody(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
