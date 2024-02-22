package com.gangE.DongoShop.repository;


import com.gangE.DongoShop.dto.PostDao;
import com.gangE.DongoShop.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Get all posts with pagination, ordered by createdAt in descending order
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

}

