package com.cmpe451.retro.services;


import com.cmpe451.retro.data.entities.Comment;
import com.cmpe451.retro.data.entities.Memory;

public interface CommentService {

    Comment getComment(Long id);

    Long addComment(Comment comment, Memory memory);

    void deleteComment(Long commentId);

    void updateComment(Comment newCommentData, Long commentId);


}
