package com.gangE.DongoShop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


// 어스에서 가능한지 확인해보기
@Entity
@Getter
@Setter
public class UserGroupMembership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String type;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_group_id")
    private UserGroupMembership group;
}
