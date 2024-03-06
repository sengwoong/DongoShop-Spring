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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String content;
    private String type;
    private int downloadCount;
    private int price;

    private LocalDateTime createdAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer ProductCustomer;


    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<PostProduct> products;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ExamProduct> examProducts;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<WordProduct> wordProducts;

    @PrePersist
    protected void onCreateTime() {
        createdAt = LocalDateTime.now();
    }


}
