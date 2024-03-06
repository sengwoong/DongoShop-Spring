package com.gangE.DongoShop.service;

import com.gangE.DongoShop.dto.PostDto;
import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.model.Post;
import com.gangE.DongoShop.repository.CustomerRepository;
import com.gangE.DongoShop.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CustomerService customerService;

    @Autowired
    public PostService(PostRepository postRepository, CustomerService customerService) {
        this.postRepository = postRepository;
        this.customerService = customerService;
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public Optional<Post> getPosts(int postId) {
        return postRepository.findById((long)postId);
    }

    public Post createPartialPost(PostDto postDto) {
        Customer customer =  customerService.getCurrentCustomer();
        if (customer == null) {
            return null; // or throw an exception
        }

        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setPostCustomer(customer);
        postRepository.save(post);
        return post;
    }

    public Optional<Post> updatePartialPost(Long postId, PostDto postDto) {
        Customer customer = customerService.getCurrentCustomer();
        if (customer == null) {
            return Optional.empty();
        }

        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            return Optional.empty();
        }

        Post post = postOptional.get();
        if (!post.getPostCustomer().equals(customer)) {
            return Optional.empty();
        }
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        return Optional.of(postRepository.save(post));
    }

    public boolean deletePartialPost(Long postId) {
        Customer customer =  customerService.getCurrentCustomer();
        if (customer == null) {
            return false;
        }

        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            return false;
        }

        Post post = postOptional.get();
        if (!post.getPostCustomer().equals(customer)) {
            return false;
        }

        postRepository.delete(post);
        return true;
    }




}
