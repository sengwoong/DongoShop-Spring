package com.gangE.DongoShop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String title;
    private String content;



    @Lob
    private byte[] img;

    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Customer postCustomer;


    @OneToMany
    @JoinColumn(name = "like_post_id")
    private List<LikePost> postLike;

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<Product> productId;

    @PrePersist
    protected void onCreateTime() {
        createdAt = LocalDateTime.now();
    }

}
