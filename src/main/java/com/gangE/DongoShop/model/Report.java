package com.gangE.DongoShop.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Authority user;

    private String reason;

    private LocalDateTime createdAt;


}