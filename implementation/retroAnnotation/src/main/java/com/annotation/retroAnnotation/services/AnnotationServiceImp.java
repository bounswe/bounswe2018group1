package com.annotation.retroAnnotation.services;

import com.annotation.retroAnnotation.data.entities.Annotation;
import com.annotation.retroAnnotation.data.repositories.AnnotationRepository;
import com.annotation.retroAnnotation.models.CreateAnnotationRequestBody;
import com.annotation.retroAnnotation.models.CreateAnnotationResponseBody;
import com.annotation.retroAnnotation.models.GetAnnotationResponseBody;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AnnotationServiceImp implements AnnotationService{

    @Autowired
    EntityManager entityManager;

    @Autowired
    AnnotationRepository annotationRepository;

    @Override
    @Transactional
    public CreateAnnotationResponseBody createAnnotation(CreateAnnotationRequestBody requestBody){

        Annotation annotation = new Annotation();
        annotation.setItemId(requestBody.getItemId());
        annotation.setUserId(requestBody.getUserId());
        annotation.setAnnotationText(requestBody.getAnnotationText());
        annotation.setType(requestBody.getType());
        annotation.setStartIndex(requestBody.getStartIndex());
        annotation.setEndIndex(requestBody.getEndIndex());

        entityManager.persist(annotation);
        entityManager.flush();

        return new CreateAnnotationResponseBody(annotation.getId());

    }

    @Override
    public GetAnnotationResponseBody getAnnotation(Long id) throws Exception {
        Optional<Annotation> annotationOptional = annotationRepository.findById(id);
        if(annotationOptional.isPresent()){
            Annotation annotation = annotationOptional.get();
            return new GetAnnotationResponseBody(annotation);
        }else{
            throw new Exception("Annotation not found");
        }

    }

    @Override
    public List<GetAnnotationResponseBody> getAnnotationsOfItem(Long itemId) {
        List<Annotation> listOfAnnotations = Lists.newArrayList(annotationRepository.findByItemId(itemId));
        List<GetAnnotationResponseBody> listOfAnnotationResponseBodies = new ArrayList<>();
        for(Annotation annotation: listOfAnnotations){
            listOfAnnotationResponseBodies.add(new GetAnnotationResponseBody(annotation));
        }

        return listOfAnnotationResponseBodies;
    }
}
