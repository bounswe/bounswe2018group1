package com.annotation.retroAnnotation.data.repositories;

import com.annotation.retroAnnotation.data.entities.Annotation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface AnnotationRepository extends CrudRepository<Annotation,Long> {

    Optional<Annotation> findById(Long id);

    List<Annotation> findAll();

    List<Annotation> findByItemId(Long itemId);


}
