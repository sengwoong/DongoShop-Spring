package com.gangE.DongoShop.repository;

import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.model.LikePost;
import com.gangE.DongoShop.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikePost, Long> {


    Long countLikePostByPost_Id(Long postId);

    LikePost findByUserAndPost(Customer customer, Post post);
}
