package com.annotation.retroAnnotation.models;

/**
 * Created by buseece on 30.12.2018.
 */
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
