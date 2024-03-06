package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.dto.CommentPostDto;
import com.gangE.DongoShop.model.CommentPost;
import com.gangE.DongoShop.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comments Controller", description = "포스트 댓글")
@RestController
@RequestMapping("/comments")
public class CommentPostController {

    private final CommentService commentService;

    @Autowired
    public CommentPostController(CommentService commentService ) {
        this.commentService = commentService;
    }

    // 페이징으로 바꾸기
    @Operation(summary = "find_all paging", description = "해당 포스트의 모든 댓글을 페이징하여 가져옵니다.")
    @GetMapping("/find_all/{postId}")
    public Page<CommentPost> getAllCommentsByPostId(@PathVariable Long postId,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        return commentService.getAllCommentsByPostId(postId, page, size);
    }
    @Operation(summary = "delete commnet", description = "내가 적은 댓글이면 해당 댓글의 아이디를 받아 삭제합니다.")
    @DeleteMapping("/delete/post/{postId}/comment/{commentId}")
    public void deleteCommentByPostIdAndUserId(@PathVariable Long postId,@PathVariable long commentId)  {
        commentService.deleteCommentsByPostId(postId,commentId);
    }
    @Operation(summary = "create", description = "포스트 아이디를받아 해당 포스트의 커맨드를 생성합니다.")
    @PostMapping("/create/{postId}")
    public void createComment(@PathVariable Long postId, @RequestBody CommentPostDto commentPostDto) {
        commentService.createNewComment(postId, commentPostDto.getCommentText() ,commentPostDto.getToUser() );
    }
    @Operation(summary = "update commnet", description = "커맨트 아이디를받아 해당 커맨드가 내가 작성 했을경우 업데이트 합니다.")
    @PutMapping("/update/{commentId}")
    public void updateComment(@PathVariable Long commentId, @RequestBody  CommentPostDto commentPostDto) {
        commentService.updateComment(commentId, commentPostDto.getCommentText(),commentPostDto.getToUser());
    }
}
