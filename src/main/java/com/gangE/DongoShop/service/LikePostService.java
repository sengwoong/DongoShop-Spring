package com.gangE.DongoShop.service;

import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.model.LikePost;
import com.gangE.DongoShop.model.Post;
import com.gangE.DongoShop.repository.CustomerRepository;
import com.gangE.DongoShop.repository.LikeRepository;
import com.gangE.DongoShop.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LikePostService {
    private static final Logger logger = LoggerFactory.getLogger(LikePostService.class);

    private final LikeRepository likeRepository;

    private final PostRepository postRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public LikePostService(LikeRepository likeRepository, CustomerRepository customerRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.customerRepository = customerRepository;
        this.postRepository = postRepository;
    }

    // 해당 포스트의 좋아요 개수 조회
    public Long countLikePostByPostId(Long postId) {
        return likeRepository.countLikePostByPost_Id(postId);
    }

    // 특정 사용자가 특정 포스트에 대한 좋아요를 했는지 확인
    public String findLikePostByUserIdAndPostId(Long postId) {

        Customer customer =  customerRepository.getCurrentCustomer();

        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isEmpty()) {
            return "포스트가 없습니다.";
        }
        Post post = postOptional.get();


        LikePost content = likeRepository.findByUserAndPost(customer, post);

        if (content == null) {
            return "좋아요 중이 아닙니다.";
        }
        return "좋아요";


    }

    // 좋아요 생성
    public String createLikePost(long postId) {

        Customer customer =  customerRepository.getCurrentCustomer();
        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isEmpty()) {
            return "포스트가 없습니다.";
        }

        Post post = postOptional.get(); // Optional에서 Post 객체를 가져옵니다.

        LikePost likePost = new LikePost();
        likePost.setPost(post);
        likePost.setUser(customer);
        likePost.setCreatedAt(LocalDateTime.now());

        likeRepository.save(likePost);

        return "좋아요 성공";

    }

    //todo 포스트가 없어도 되야하니 수정해야 합니다.
    public String deleteLikePostByUserIdAndPostId(Long postId) {

        Customer customer =  customerRepository.getCurrentCustomer();

        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isEmpty()) {
            return "포스트가 없습니다.";
        }

        Post post = postOptional.get();

        LikePost likePost = likeRepository.findByUserAndPost(customer, post);

        if (likePost == null) {
            return "아직 좋아요 중이 아닙니다.";
        } else {
            likeRepository.delete(likePost);
            return "좋아요 취소 성공";
        }
    }


}