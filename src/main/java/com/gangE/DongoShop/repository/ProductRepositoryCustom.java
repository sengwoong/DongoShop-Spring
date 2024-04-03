package com.gangE.DongoShop.repository;

import com.gangE.DongoShop.model.Product;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepositoryCustom {


    // 타입이있으면 타입을 리턴하고 가장 많이 다운로드된 문제 와 단어 나 최신 단어가있으면 그걸리턴해야함
    //
    List<Product> searchInAllProducts(String type,String content,String downCountOrder,String currentOrder);

}
