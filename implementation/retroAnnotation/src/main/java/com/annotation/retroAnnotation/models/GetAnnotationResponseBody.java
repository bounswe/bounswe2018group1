package com.annotation.retroAnnotation.models;

import com.annotation.retroAnnotation.data.entities.Annotation;

/**
 * Created by buseece on 30.12.2018.
 */
public class GetAnnotationResponseBody {

    private Long id;

    private Long itemId;

    private Long userId;

    private String annotationText;

    private String type;

    private int startIndex;

    private int endIndex;

    public GetAnnotationResponseBody(){}

    public GetAnnotationResponseBody(Annotation annotation){
        this.id = annotation.getId();
        this.itemId = annotation.getItemId();
        this.userId = annotation.getUserId();
        this.annotationText = annotation.getAnnotationText();
        this.type = annotation.getType();
        this.startIndex = annotation.getStartIndex();
        this.endIndex = annotation.getEndIndex();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAnnotationText() {
        return annotationText;
    }

    public void setAnnotationText(String annotationText) {
        this.annotationText = annotationText;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
}
