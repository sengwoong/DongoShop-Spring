package com.gangE.DongoShop.repository;


import com.gangE.DongoShop.model.WordProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordProductRepository extends JpaRepository<WordProduct, Long>{




}
