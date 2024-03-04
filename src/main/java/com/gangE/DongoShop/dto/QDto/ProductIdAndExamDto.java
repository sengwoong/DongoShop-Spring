package com.gangE.DongoShop.dto.QDto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Setter
@Getter
@DynamicUpdate
@NoArgsConstructor
public class ProductIdAndExamDto {

    private int product_id;
    private int exam_id;
    private String title;

    private String content;


    @QueryProjection
    public ProductIdAndExamDto(int product_id, int exam_id, String title, String content) {
        this.product_id = product_id;
        this.exam_id = exam_id;
        this.title = title;
        this.content = content;
    }


}
