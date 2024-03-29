package com.gangE.DongoShop.repository;

import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.gangE.DongoShop.model.CommentPost;
import org.springframework.data.jpa.repository.Modifying;
import java.util.List;

@Repository
public interface CommentPostRepository extends JpaRepository<CommentPost, Long> {



    @Modifying
    @Query(value = "INSERT INTO comment_post (post_id, user_id, comment_text, to_user) VALUES (:postId, :userId, :commentText, :toUser)", nativeQuery = true)
    void createNewComment(Long postId, Long userId, String commentText, Long toUser);

    Page<CommentPost> findAllByPostId(Long postId, Pageable pageable);
}
