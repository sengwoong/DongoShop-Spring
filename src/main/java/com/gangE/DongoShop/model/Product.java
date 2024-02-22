package com.gangE.DongoShop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private int contentId;
    private String type;

    private int downloadCount;
    private int price;

    @OneToMany(mappedBy = "product")
    private Set<ExamProduct> examProducts;


    @OneToMany(mappedBy = "product")
    private List<WordProduct> wordProducts;

}
