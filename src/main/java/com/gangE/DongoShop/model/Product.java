package com.gangE.DongoShop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;
import java.util.Set;

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
    private int contentId;
    private String type;

    private int downloadCount;
    private int price;



    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;


    @OneToMany(mappedBy = "product")
    private Set<ExamProduct> examProducts;



    @OneToMany(mappedBy = "product")
    private List<WordProduct> wordProducts;

}
