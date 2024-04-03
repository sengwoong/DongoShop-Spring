package com.gangE.DongoShop.repository;

import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> ,ProductRepositoryCustom{
    Page<Product> findByUser(Customer user, Pageable pageable);

    Page<Product> findByVisible(Boolean visible, Pageable pageable);

}
