package com.gangE.DongoShop.service;

import com.gangE.DongoShop.model.CommentPost;
import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.model.Post;
import com.gangE.DongoShop.repository.CommentPostRepository;
import com.gangE.DongoShop.repository.CustomerRepository;
import com.gangE.DongoShop.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommentService {

    private final CommentPostRepository commentRepository;
    private final CustomerRepository customerRepository;
    private final PostRepository postRepository;

    private final CustomerService customerService;


    @Autowired
    public CommentService(CommentPostRepository commentRepository, CustomerRepository customerRepository, PostRepository postRepository,CustomerService customerService) {
        this.commentRepository = commentRepository;
        this.customerRepository = customerRepository;
        this.postRepository = postRepository;
        this.customerService = customerService;
    }

    /**
     * 새로운 댓글 생성
     */
    @Transactional
    public void createNewComment(Long postId, String commentText, Long toUser) {
        Customer customer = customerService.getCurrentCustomer();

        // 대상 사용자가 있는 경우 대상 사용자 정보 가져오기
        Customer targetUser = (toUser != null) ? customerRepository.findById(toUser).orElse(null) : null;

        commentRepository.createNewComment(postId, (long) customer.getId(), commentText, (targetUser != null) ? (long) targetUser.getId() : null);
    }

    /**
     * 주어진 게시물 ID에 대한 모든 댓글 가져오기
     */
    @Transactional(readOnly = true)
    public Page<CommentPost> getAllCommentsByPostId(Long postId, int page, int size) {
        // 페이지네이션에 사용할 Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size);

        // postId로 해당하는 Post를 찾음
        Optional<Post> postOptional = postRepository.findById(postId);
        Post post = postOptional.orElse(null);

        if (post == null) {
            // Post가 없는 경우 빈 페이지를 반환
            return Page.empty();
        }

        // 해당 Post에 속한 모든 Comment를 페이징하여 반환
        return commentRepository.findAllByPostId(postId, pageable);

    }
    /**
     * 댓글 삭제
     */
    @Transactional
    public void deleteCommentsByPostId(Long postId, long commentId) {
        Customer customer = customerService.getCurrentCustomer();

        Optional<CommentPost> commentOptional = commentRepository.findById(commentId);
        CommentPost comment = commentOptional.orElse(null);

        if (comment != null && comment.getUser().getId() == customer.getId()) {
            commentRepository.delete(comment);
        }
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public void updateComment(Long commentId, String newContent, Long toUser) {
        Customer customer = customerService.getCurrentCustomer();

        Optional<CommentPost> commentOptional = commentRepository.findById(commentId);
        CommentPost comment = commentOptional.orElse(null);

        if (comment != null && comment.getUser().getId() == customer.getId()) {
            // 대상 사용자 정보가 제공된 경우 업데이트
            if (toUser != null) {
                Customer targetUser = customerRepository.findById(toUser).orElse(null);
                if (targetUser != null) {
                    comment.setToUser(String.valueOf(targetUser.getId()));
                }
            }
            comment.setCommentText(newContent);
            commentRepository.save(comment);
        }
    }



}
