package com.gangE.DongoShop.service;

import com.gangE.DongoShop.dto.PostDao;
import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.model.Post;
import com.gangE.DongoShop.repository.CustomerRepository;
import com.gangE.DongoShop.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public PostService(PostRepository postRepository, CustomerRepository customerRepository) {
        this.postRepository = postRepository;
        this.customerRepository = customerRepository;
    }


// 포스트 유저가 로그인한 토큰을록 분리하여 서비스 등록
// 로그인시만 포스트 등록이 가능
    public Post createPartialPost(PostDao postDao) {
        // 현재 인증된 사용자의 이름 가져오기
        String username = SecurityContextHolder.getContext().getAuthentication().getName();




        logger.info("SecurityContextHolder.getContext().getAuthentication:{}", SecurityContextHolder.getContext().getAuthentication());
        logger.info("Creating partial post for user: {}", username);
        logger.info("Creating partial post for user: {}", username);
        logger.info("Creating partial post for user: {}", username);
        logger.info("Creating partial post for user: {}", username);
        logger.info("Creating partial post for user: {}", username);
        logger.info("Creating partial post for user: {}", username);
        logger.info("Creating partial post for user: {}", username);

        // CustomerRepository를 사용하여 현재 사용자를 찾습니다.
        Customer customer = customerRepository.findByName(username);

        // 로그인 안했을때 포스트 등록을 막아야함 (토큰없을때도 예외처)
        logger.info("customer {}", customer);
        logger.info("customer: {}", customer);
        logger.info("customer: {}", customer);
        logger.info("customer {}", customer);

        // Post 객체 생성 및 속성 설정
        Post post = new Post();
        post.setContent(postDao.getContent());
        post.setTitle(postDao.getTitle());
        post.setImg(postDao.getImg());
        post.setCreatedAt(LocalDateTime.now());
        post.setPostCustomer(customer);

        // 생성된 Post 저장 및 반환
        return postRepository.save(post);
    }








}
