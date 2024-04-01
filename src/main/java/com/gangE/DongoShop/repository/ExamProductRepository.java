package com.gangE.DongoShop.repository;

import com.gangE.DongoShop.model.ExamProduct;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamProductRepository extends JpaRepository<ExamProduct, Long> {


//    @Query("SELECT MAX(ep.examLocal) FROM ExamProduct ep WHERE ep.product.id = :productId")
//    Integer findMaxExamLocalByProduct(@Param("productId") Long productId);


    ExamProduct findByProductIdAndExamLocal(int productId, int prevId);

    @Query("SELECT MAX(ep.examLocal) FROM ExamProduct ep WHERE ep.product.id = :product")
    Integer findMaxExamLocalByProduct(@Param("product") long product);

}
