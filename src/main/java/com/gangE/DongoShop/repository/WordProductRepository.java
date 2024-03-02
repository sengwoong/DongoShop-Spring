package com.gangE.DongoShop.repository;


import com.gangE.DongoShop.model.WordProduct;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WordProductRepository extends JpaRepository<WordProduct, Long>{


    @Modifying
    @Query("DELETE FROM WordProduct w WHERE w.word.id = :wordId")
    void deleteByProductIdAndWordId(@Param("wordId") Long wordId);


}
