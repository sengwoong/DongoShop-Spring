package com.gangE.DongoShop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Authority user;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    private String content;

    private LocalDateTime createdAt;

    // Getters and Setters
}