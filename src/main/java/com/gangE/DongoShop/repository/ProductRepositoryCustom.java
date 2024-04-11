package com.gangE.DongoShop.repository;

import com.gangE.DongoShop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepositoryCustom {
    Page<Product> searchInAllProducts(String type, String content, String downCountOrder, String currentOrder, int page, int size);
}
