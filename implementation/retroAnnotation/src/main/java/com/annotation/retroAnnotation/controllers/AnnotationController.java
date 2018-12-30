package com.annotation.retroAnnotation.controllers;

import com.annotation.retroAnnotation.models.CreateAnnotationRequestBody;
import com.annotation.retroAnnotation.models.CreateAnnotationResponseBody;
import com.annotation.retroAnnotation.models.GetAnnotationResponseBody;
import com.annotation.retroAnnotation.services.AnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", allowCredentials ="true", allowedHeaders ="*",
        methods = {RequestMethod.HEAD,RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})

@RestController
public class AnnotationController {

    @Autowired
    AnnotationService annotationService;

    @RequestMapping(value = "/annotation",method = RequestMethod.POST)
    public CreateAnnotationResponseBody createAnnotation(@RequestBody CreateAnnotationRequestBody requestBody){
        return annotationService.createAnnotation(requestBody);
    }

    @RequestMapping(value = "/annotation",method = RequestMethod.GET)
    public GetAnnotationResponseBody getAnnotation(Long id) throws Exception {
        return annotationService.getAnnotation(id);
    }

    @RequestMapping(value = "/annotation/item",method = RequestMethod.GET)
    public List<GetAnnotationResponseBody> getAnnotationOfItem(Long itemId) {
        return annotationService.getAnnotationsOfItem(itemId);
    }

    @RequestMapping(value = "/annotation",method = RequestMethod.DELETE)
    public void deleteAnnotation(Long id, Long userId) throws Exception {
        annotationService.deleteAnnotation(id,userId);
    }



}
