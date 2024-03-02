package com.gangE.DongoShop.repository;


import com.gangE.DongoShop.dto.QDto.ProductIdAndWordDto;
import com.gangE.DongoShop.model.Product;
import com.gangE.DongoShop.model.Word;
import com.gangE.DongoShop.model.WordProduct;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WordRepositoryCustom {
    List<ProductIdAndWordDto> searchInAllWord(Product product);


    WordProduct searchInProductWord(Product product, long word);






}
