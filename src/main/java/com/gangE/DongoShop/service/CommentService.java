package com.gangE.DongoShop.service;

import com.gangE.DongoShop.model.CommentPost;
import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.model.Post;
import com.gangE.DongoShop.repository.CommentPostRepository;
import com.gangE.DongoShop.repository.CustomerRepository;
import com.gangE.DongoShop.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentPostRepository commentRepository;
    private final CustomerRepository customerRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentPostRepository commentRepository, CustomerRepository customerRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.customerRepository = customerRepository;
        this.postRepository = postRepository;
    }

    /**
     * 새로운 댓글 생성
     */
    @Transactional
    public void createNewComment(Long postId, String commentText, Long toUser) {
        Customer customer = customerRepository.getCurrentCustomer();

        // 대상 사용자가 있는 경우 대상 사용자 정보 가져오기
        Customer targetUser = (toUser != null) ? customerRepository.findById(toUser).orElse(null) : null;

        commentRepository.createNewComment(postId, (long) customer.getId(), commentText, (targetUser != null) ? (long) targetUser.getId() : null);
    }

    /**
     * 주어진 게시물 ID에 대한 모든 댓글 가져오기
     */
    @Transactional(readOnly = true)
    public List<CommentPost> getAllCommentsByPostId(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        Post post = postOptional.orElse(null);

        if (post == null) {
            return Collections.emptyList();
        }

        return commentRepository.findAllByPost(post);
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public void deleteCommentsByPostId(Long postId, long commentId) {
        Customer customer = customerRepository.getCurrentCustomer();

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
        Customer customer = customerRepository.getCurrentCustomer();

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

    /**
     * 현재 인증된 사용자 정보 가져오기
     */

}
