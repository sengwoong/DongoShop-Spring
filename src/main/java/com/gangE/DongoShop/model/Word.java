package com.gangE.DongoShop.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Setter
@Getter
@DynamicUpdate
@NoArgsConstructor
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String word;


    private String definition;

    @JsonIgnore
    @OneToOne(mappedBy = "word")
    private WordProduct wordProduct;

}
