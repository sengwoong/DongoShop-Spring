package com.gangE.DongoShop.repository;

import com.gangE.DongoShop.model.ExamProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamProductRepository extends JpaRepository<ExamProduct, Long> {



}
