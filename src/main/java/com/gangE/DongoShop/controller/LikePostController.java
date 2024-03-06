package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.service.LikePostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Like Controller", description = "문제")
@RestController
@RequestMapping("/like")
public class LikePostController {

    private final LikePostService likePostService;

    @Autowired
    public LikePostController(LikePostService likePostService) {
        this.likePostService = likePostService;
    }

    // 해당 포스트의 좋아요 개수 조회
    @Operation(summary = "count", description = "포스트의 아이디를 파라미터로 받고 포스트의 좋아요 총 개수를 얻습니다.")
    @GetMapping("/count/{postId}")
    public ResponseEntity<?> countLikePostByPostId(@PathVariable Long postId) {
        try {
            Long count = likePostService.countLikePostByPostId(postId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("좋아요 개수 조회에 실패했습니다.");
        }
    }

    @Operation(summary = "find", description = "포스트의 아이디를 파라미터로 받고 내가 포스트의 좋아요 를 하였는지 확인을 합니다.")
    @GetMapping("/find/{postId}")
    public ResponseEntity<?> findLikePostByUserIdAndPostId(@PathVariable Long postId) {
        try {
            String likePost = likePostService.findLikePostByUserIdAndPostId(postId);
            return ResponseEntity.ok(likePost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("좋아요 확인에 실패했습니다.");
        }
    }



    @Operation(summary = "create", description = "포스트의 아이디를 파라미터로 받고 내가 포스트의 좋아요 를 생성 합니다.")
    @PostMapping("/create/{postId}")
    public ResponseEntity<?> createLikePost(@PathVariable Long postId) {
        try {
            String likePost = likePostService.createLikePost(postId);
            return ResponseEntity.ok(likePost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("좋아요 생성에 실패했습니다.");
        }
    }


    @Operation(summary = "delect", description = "포스트의 아이디를 파라미터로 받고 내가 포스트의 좋아요 를 삭제 합니다.")
    @DeleteMapping("/delect/{postId}")
    public ResponseEntity<?> deleteLikePost(@PathVariable Long postId) {
        try {

            String unlike = likePostService.deleteLikePostByUserIdAndPostId( postId);
            return ResponseEntity.ok(unlike);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("좋아요 취소 실");
        }
    }

}
