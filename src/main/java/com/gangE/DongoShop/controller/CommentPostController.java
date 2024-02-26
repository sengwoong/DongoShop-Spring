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

    // todo DeleteMapping 은 서비스로 만들어야 할 것 같습니다.
    @DeleteMapping("/delete/post/{postId}/comment/{commentId}")
    public void deleteCommentByPostIdAndUserId(@PathVariable Long postId,@PathVariable long commentId)  {
        commentService.deleteCommentsByPostId(postId,commentId);
    }

    @PostMapping("/create/{postId}")
    public void createComment(@PathVariable Long postId, @RequestBody CommentPostDto commentPostDto) {
        commentService.createNewComment(postId, commentPostDto.getCommentText() ,commentPostDto.getToUser() );
    }

    // 다른 컨트롤러 메서드들을 필요에 따라 추가할 수 있습니다.
}
