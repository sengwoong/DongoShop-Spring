package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.dto.PostDto;
import com.gangE.DongoShop.dto.PostIdProductIdDto;
import com.gangE.DongoShop.model.Post;
import com.gangE.DongoShop.service.PostProductService;
import com.gangE.DongoShop.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    private final PostProductService postProductService;
    @Autowired
    public PostController(PostService postService, PostProductService postProductService) {
        this.postService = postService;
        this.postProductService = postProductService;
    }

    @GetMapping("select_all")
    public ResponseEntity<Page<Post>> getAllPosts(Pageable pageable) {
        Page<Post> posts = postService.getAllPosts(pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("select/{postId}")
    public ResponseEntity<Optional<Post>> getPostById(@PathVariable(value = "postId") int postId) {
        Optional<Post> post = postService.getPosts(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Post> createPost( @RequestBody PostDto postDto) {
        Post savedPost = postService.createPartialPost(postDto);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @PutMapping("update/{postId}")
    public ResponseEntity<Optional<Post> > updatePost(@PathVariable(value = "postId") Long postId,
                                           @RequestBody PostDto postDto) {
        Optional<Post> updatedPost = postService.updatePartialPost(postId, postDto);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("delect/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable(value = "postId") Long postId) {
        postService.deletePartialPost(postId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("connect/post/{postId}/product/{productId}")
    public ResponseEntity<?> connectPost(@PathVariable(value = "postId") int postId, @PathVariable(value = "productId") int productId) {
        PostIdProductIdDto postIdProductIdDto = new PostIdProductIdDto(postId, productId);
        postProductService.connectPostProduct(postIdProductIdDto);
        return ResponseEntity.ok().build();
    }


}
