package com.gangE.DongoShop.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer postCustomer;

    private String title;
    private String content;



//    @Lob
//    private byte[] img;

    private LocalDateTime createdAt;


    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<LikePost> postLike;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<CommentPost> commentPost;

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private List<PostProduct> products;


    @PrePersist
    protected void onCreateTime() {
        createdAt = LocalDateTime.now();
    }

}
