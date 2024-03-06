package com.gangE.DongoShop.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;



@Entity
@Getter
@Setter
@DynamicInsert
public class CommentPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Customer user;

    @Column(name = "to_user")
    private String toUser;

    @Column(name = "comment_text")
    private String commentText;

}
