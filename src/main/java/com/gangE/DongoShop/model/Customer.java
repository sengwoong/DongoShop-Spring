package com.gangE.DongoShop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


import java.util.Set;

@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
public final class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pwd;

    private String role;

    @Column(name = "create_dt")
    private String createDt;

    @JsonIgnore
    @OneToMany(mappedBy="customer",fetch=FetchType.LAZY)
    private Set<Authority> authorities;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Point point;


}
