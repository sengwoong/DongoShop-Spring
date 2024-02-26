package com.gangE.DongoShop.repository;

import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.model.LikePost;
import com.gangE.DongoShop.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikePost, Long> {


    Long countLikePostByPost_Id(Long postId);

    // 내가 좋아요를 했는지 검색


    LikePost findLikePostByUser_IdAndPost_Id(Customer customer, Long postId);

    void deleteByPostAndUser(Customer customer, Post post);

    LikePost findByUserAndPost(Customer customer, Post post);
}
