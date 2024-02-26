package com.gangE.DongoShop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"post_id", "user_id"})
})
public class LikePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer user;

    private LocalDateTime createdAt;


}
