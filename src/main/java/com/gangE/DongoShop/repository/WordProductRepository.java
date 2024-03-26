package com.gangE.DongoShop.repository;


import com.gangE.DongoShop.model.Product;
import com.gangE.DongoShop.model.WordProduct;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WordProductRepository extends JpaRepository<WordProduct, Long>{
    WordProduct findByProductIdAndWordLocal(int productId, int wordId);

    @Query("SELECT MAX(wp.wordLocal) FROM WordProduct wp WHERE wp.product.id = :productId")
    Integer findMaxWordLocalByProduct(@Param("productId") Long productId);

}
