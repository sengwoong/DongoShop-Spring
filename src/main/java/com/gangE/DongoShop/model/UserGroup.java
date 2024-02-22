package com.gangE.DongoShop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Authority user;

    @OneToMany
    @JoinColumn(name = "user_group_membership_id")
    private UserGroupMembership group;
}
