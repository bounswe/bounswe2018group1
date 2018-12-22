package com.cmpe451.retro.data.repositories;

import com.cmpe451.retro.data.entities.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
