package com.gangE.DongoShop.dto.QDto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Setter
@Getter
@NoArgsConstructor
public class ProductIdAndWordDto {

    private int product_id;
    private int word_id;
    private String word;
    private int wordLocal;
    private String definition;


    @QueryProjection
    public ProductIdAndWordDto(int product_id, int word_id,int wordLocal,String word, String definition) {
        this.product_id = product_id;
        this.word_id = word_id;
        this.wordLocal = wordLocal;
        this.word = word;
        this.definition = definition;
    }


}
