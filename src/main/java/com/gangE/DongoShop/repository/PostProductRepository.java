package com.gangE.DongoShop.repository;


import com.gangE.DongoShop.model.PostProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostProductRepository extends JpaRepository<PostProduct, Long> {


}

