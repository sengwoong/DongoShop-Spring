package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.dto.PostDto;
import com.gangE.DongoShop.dto.PostIdProductIdDto;
import com.gangE.DongoShop.model.Post;
import com.gangE.DongoShop.service.PostProductService;
import com.gangE.DongoShop.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = " Post Controller", description = "포스트")
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
    @Operation(summary = "select_all paging", description = "포스트를 페이지네이션하며 들고옵니다")
    @GetMapping("select_all")
    public ResponseEntity<Page<Post>> getAllPosts(Pageable pageable) {
        Page<Post> posts = postService.getAllPosts(pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    @Operation(summary = "select", description = "포스트아이들 기준으로 포스트 하나를 들고옵니다.")
    @GetMapping("select/{postId}")
    public ResponseEntity<Optional<Post>> getPostById(@PathVariable(value = "postId") int postId) {
        Optional<Post> post = postService.getPosts(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @Operation(summary = "create", description = "포스트를 생성 합니다.")
    @PostMapping("/create")
    public ResponseEntity<Post> createPost( @RequestBody PostDto postDto) {
        Post savedPost = postService.createPartialPost(postDto);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @Operation(summary = "update", description = "포스트 파람을받으며 자신이 자작성한 포스트를 수정 합니다.")
    @PutMapping("update/{postId}")
    public ResponseEntity<Optional<Post> > updatePost(@PathVariable(value = "postId") Long postId,
                                           @RequestBody PostDto postDto) {
        Optional<Post> updatedPost = postService.updatePartialPost(postId, postDto);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }
    @Operation(summary = "delect", description = "포스트 파람을받으며 자신이 자작성한 포스트를 삭제 합니다.")
    @DeleteMapping("delect/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable(value = "postId") Long postId) {
        postService.deletePartialPost(postId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "connect", description = "포스에 프로덕트 정보를 넣습니다.")
    @PostMapping("connect/post/{postId}/product/{productId}")
    public ResponseEntity<?> connectPost(@PathVariable(value = "postId") int postId, @PathVariable(value = "productId") int productId) {
        PostIdProductIdDto postIdProductIdDto = new PostIdProductIdDto(postId, productId);
        postProductService.connectPostProduct(postIdProductIdDto);
        return ResponseEntity.ok().build();
    }


}
