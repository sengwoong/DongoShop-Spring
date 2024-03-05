package com.gangE.DongoShop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Customer user;

    private String message;

    private LocalDateTime time;


    // createdAt 필드 추가
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 엔티티가 저장되기 전에 실행되는 메소드를 추가하여 createdAt 필드에 현재 시간을 할당합니다.
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }


}