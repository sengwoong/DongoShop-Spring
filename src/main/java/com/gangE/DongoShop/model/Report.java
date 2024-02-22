package com.gangE.DongoShop.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Customer user;

    private String reason;

    private LocalDateTime createdAt;


}