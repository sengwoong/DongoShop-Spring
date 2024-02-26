package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.dto.PostDao;
import com.gangE.DongoShop.model.LikePost;
import com.gangE.DongoShop.model.Post;
import com.gangE.DongoShop.repository.PostRepository;
import com.gangE.DongoShop.service.LikePostService;
import com.gangE.DongoShop.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/like")
public class LikePostController {

    private final LikePostService likePostService;

    @Autowired
    public LikePostController(LikePostService likePostService) {
        this.likePostService = likePostService;
    }

    // 해당 포스트의 좋아요 개수 조회
    @GetMapping("/count/{postId}")
    public ResponseEntity<?> countLikePostByPostId(@PathVariable Long postId) {
        try {
            Long count = likePostService.countLikePostByPostId(postId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("좋아요 개수 조회에 실패했습니다.");
        }
    }

    // 특정 사용자가 특정 포스트에 좋아요를 했는지 확인
    @GetMapping("/find/{postId}")
    public ResponseEntity<?> findLikePostByUserIdAndPostId(@PathVariable Long postId) {
        try {
            String likePost = likePostService.findLikePostByUserIdAndPostId(postId);
            return ResponseEntity.ok(likePost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("좋아요 확인에 실패했습니다.");
        }
    }


    // 좋아요 생성
    @PostMapping("/create/{postId}")
    public ResponseEntity<?> createLikePost(@PathVariable Long postId) {
        try {
            String likePost = likePostService.createLikePost(postId);
            return ResponseEntity.ok(likePost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("좋아요 생성에 실패했습니다.");
        }
    }

    // 좋아요 삭제
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
