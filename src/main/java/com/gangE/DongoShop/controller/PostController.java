package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.dto.PostDao;
import com.gangE.DongoShop.model.Post;
import com.gangE.DongoShop.repository.PostRepository;
import com.gangE.DongoShop.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;

    @Autowired
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    private PostService postService;


    // 모든 게시물을 페이징하여 반환합니다.
    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // 특정 ID의 게시물을 반환합니다.
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable(value = "postId") Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow();
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // 새로운 게시물을 생성합니다.
    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody PostDao post) {
        Post savedPost = postService.createPartialPost(post);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    // 기존의 게시물을 수정합니다.
    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable(value = "postId") Long postId,
                                           @Valid @RequestBody Post postDetails) {
        Post post = postRepository.findById(postId)
                .orElseThrow();

        post.setContent(postDetails.getContent());
        post.setImg(postDetails.getImg());
        // 나머지 필드도 마찬가지로 업데이트합니다.

        Post updatedPost = postRepository.save(post);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    // 특정 ID의 게시물을 삭제합니다.
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable(value = "postId") Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow();

        postRepository.delete(post);

        return ResponseEntity.ok().build();
    }
}
