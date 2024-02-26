package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.dto.CommentPostDto;
import com.gangE.DongoShop.model.CommentPost;
import com.gangE.DongoShop.repository.CommentPostRepository;
import com.gangE.DongoShop.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentPostController {

    private final CommentService commentService;


    @Autowired
    public CommentPostController(CommentService commentService ) {
        this.commentService = commentService;


    }





    @GetMapping("/find_all/{postId}")
    public List<CommentPost> getAllCommentsByPostId(@PathVariable Long postId) {
        return commentService.getAllCommentsByPostId(postId);
    }

    @DeleteMapping("/delete/post/{postId}/comment/{commentId}")
    public void deleteCommentByPostIdAndUserId(@PathVariable Long postId,@PathVariable long commentId)  {
        commentService.deleteCommentsByPostId(postId,commentId);
    }

    @PostMapping("/create/{postId}")
    public void createComment(@PathVariable Long postId, @RequestBody CommentPostDto commentPostDto) {
        commentService.createNewComment(postId, commentPostDto.getCommentText() ,commentPostDto.getToUser() );
    }

    @PutMapping("/update/{commentId}")
    public void updateComment(@PathVariable Long commentId, @RequestBody  CommentPostDto commentPostDto) {
        commentService.updateComment(commentId, commentPostDto.getCommentText(),commentPostDto.getToUser());
    }
}
