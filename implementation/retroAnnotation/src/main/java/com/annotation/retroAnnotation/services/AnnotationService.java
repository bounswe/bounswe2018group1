package com.annotation.retroAnnotation.services;


import com.annotation.retroAnnotation.models.GetAnnotationResponseBody;
import com.annotation.retroAnnotation.models.CreateAnnotationRequestBody;
import com.annotation.retroAnnotation.models.CreateAnnotationResponseBody;

import java.util.List;

public interface AnnotationService {

    CreateAnnotationResponseBody createAnnotation(CreateAnnotationRequestBody requestBody);

    GetAnnotationResponseBody getAnnotation(Long id) throws Exception;

    List<GetAnnotationResponseBody> getAnnotationsOfItem(Long itemId);

    void deleteAnnotation(Long id, Long userId) throws Exception;

}
