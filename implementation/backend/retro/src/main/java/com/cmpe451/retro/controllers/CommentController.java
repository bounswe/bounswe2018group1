package com.cmpe451.retro.controllers;

import com.cmpe451.retro.data.entities.Comment;
import com.cmpe451.retro.data.entities.Memory;
import com.cmpe451.retro.models.ErrorResponse;
import com.cmpe451.retro.models.GetMemoryResponseBody;
import com.cmpe451.retro.services.CommentService;
import com.cmpe451.retro.services.MemoryService;
import com.cmpe451.retro.services.UserService;
import org.apache.tomcat.jni.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private MemoryService memoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/memory/{memoryId}/comments", method = RequestMethod.GET)
    public List<Comment> showComments(@PathVariable("memoryId") Long memoryId) { //, ModelMap model
        GetMemoryResponseBody getMemoryResponseBody = memoryService.getMemory(memoryId);

        List<Comment> comments = getMemoryResponseBody.getListOfComments();

        return comments;
        //model.addAttribute("comments", comments);
        //model.addAttribute("post", post);

        //return "fragments/comments :: commentList";
    }

    //@PreAuthorize("hasRole('ROLE_USER')")
    /*@RequestMapping(value = "/memory/{memoryId}/comments/create", method = RequestMethod.POST)
    public @ResponseBody String addComment(@Valid @ModelAttribute(value = "comment") Comment comment,
                                           @PathVariable("memoryId") Long memoryId) {

        GetMemoryResponseBody getMemoryResponseBody = memoryService.getMemory(memoryId);

        if (getMemoryResponseBody == null)
            return globalExceptionHandler("error", "post not found");


        Long addedId = commentService.saveNewComment(comment, getMemoryResponseBody);

        return makeCommentAddResponse("ok", addedId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/posts/{postId}/comments/{commentId}/delete", method = RequestMethod.POST)
    public @ResponseBody String deleteComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
        try {
            commentService.deleteComment(commentId);
        } catch (ActionExpiredException e) {
            return "expired";
        }

        return "ok";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/posts/{postId}/comments/{commentId}/edit", method = RequestMethod.POST)
    public @ResponseBody String editComment(@Valid @ModelAttribute(value = "comment") Comment comment, BindingResult result,
                                            @PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
        if (result.hasErrors()) {
            return result.getAllErrors().get(0).getDefaultMessage();
        }

        try {
            commentService.updateComment(comment, commentId);
        } catch (ActionExpiredException e) {
            return "expired";
        }

        return "ok";
    }
*/


}
