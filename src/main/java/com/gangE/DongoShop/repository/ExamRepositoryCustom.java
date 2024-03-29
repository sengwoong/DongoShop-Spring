package com.gangE.DongoShop.repository;


import com.gangE.DongoShop.dto.QDto.ProductIdAndExamDto;
import com.gangE.DongoShop.model.ExamProduct;
import com.gangE.DongoShop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepositoryCustom {


    // word productid DTO로만들기
    List<ProductIdAndExamDto> searchInAllExam(Product product);

    ExamProduct searchInProductExam(Product product, long searchExam);
}
