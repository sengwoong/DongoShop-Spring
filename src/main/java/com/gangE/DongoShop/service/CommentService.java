package com.gangE.DongoShop.service;



import com.gangE.DongoShop.model.CommentPost;
import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.model.LikePost;
import com.gangE.DongoShop.model.Post;
import com.gangE.DongoShop.repository.CommentPostRepository;
import com.gangE.DongoShop.repository.CustomerRepository;
import com.gangE.DongoShop.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public CommentService(CommentPostRepository commentRepository, CustomerRepository customerRepository, PostRepository postRepository ) {
        this.postRepository =postRepository;
        this.commentRepository = commentRepository;
        this.customerRepository = customerRepository;
    }



    @Transactional
    public void createNewComment(Long postId, String commentText, Long toUser) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findByName(username);

        if (customer == null) {
            throw new RuntimeException("로그인한 사용자를 찾을 수 없습니다.");
        }

        if (toUser != null) {
            // 대상 사용자 정보 가져오기
            Customer getToUser = customerRepository.findById(toUser)
                    .orElseThrow(() -> new RuntimeException("대상 사용자를 찾을 수 없습니다."));

            commentRepository.createNewComment(postId, (long) customer.getId(), commentText, toUser);
        } else {
            // 새로운 커맨트 생성 (대상 사용자가 없는 경우)
            commentRepository.createNewComment(postId, (long) customer.getId(), commentText);
        }
    }

    @Transactional(readOnly = true)
    public List<CommentPost> getAllCommentsByPostId(Long postId) {
        // 현재 인증된 사용자의 정보 가져오기


        // 주어진 postId에 해당하는 포스트 가져오기
        Optional<Post> postOptional = postRepository.findById(postId);

        // 포스트가 존재하지 않을 경우 빈 리스트 반환
        if (postOptional.isEmpty()) {
            return Collections.emptyList();
        }
        Post post = postOptional.get();

        // 해당 포스트의 모든 댓글 가져오기
        return commentRepository.findAllByPost(post);
    }

    @Transactional
    public void deleteCommentsByPostId(Long postId, long commentId) {
        // 현재 인증된 사용자 이름 가져오기
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // 사용자 이름으로 사용자 정보 가져오기
        Customer customer = customerRepository.findByName(username);

        // 주어진 commentId로 댓글 가져오기
        Optional<CommentPost> commentOptional = commentRepository.findById(commentId);

        // 댓글이 존재하지 않을 경우 메서드 종료
        if (commentOptional.isEmpty()) {
            return;
        }

        CommentPost comment = commentOptional.get();

        // 댓글을 작성한 사용자와 현재 인증된 사용자가 같은지 확인
        if (comment.getUser().getId() == customer.getId()) {
            // 댓글 삭제
            commentRepository.delete(comment);
        } else {

            return;
        }
    }
}
