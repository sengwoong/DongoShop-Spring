package com.gangE.DongoShop.repository;


import com.gangE.DongoShop.dto.QDto.ProductIdAndWordDto;
import com.gangE.DongoShop.model.Product;

import com.gangE.DongoShop.model.Word;
import com.gangE.DongoShop.model.WordProduct;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;


import static com.gangE.DongoShop.model.QWord.word1;
import static com.gangE.DongoShop.model.QWordProduct.wordProduct;
import static org.springframework.util.StringUtils.isEmpty;


public class WordRepositoryImpl implements WordRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public WordRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    // 페이징 으로 바꾸기
    @Override
    public List<ProductIdAndWordDto> searchInAllWord(Product product) {
        List<ProductIdAndWordDto> words = queryFactory
                .select(Projections.constructor(
                        ProductIdAndWordDto.class,
                        wordProduct.product.id,
                        word1.id,
                        word1.word,
                        word1.definition
                ))
                .from(word1)
                .innerJoin(word1.wordProduct, wordProduct)
                .where(wordProduct.product.eq(product))
                .fetch();
        return words;
    }

    @Override
    public WordProduct searchInProductWord(Product product, long word) {
        WordProduct words = queryFactory
                .select(wordProduct)
                .from(wordProduct)
                .innerJoin(wordProduct.word, word1)
                .where(
                        wordProduct.product.eq(product),
                        wordIdEq(word)
                )
                .fetchFirst();

        return words;
    }


    private BooleanExpression wordIdEq(long word) {
        return isEmpty(word) ? null : word1.id.eq((int) word);
    }



//
//
//    @Override
//    public Page<AllWordDto> searchAllWords(Product product, Pageable pageable) {
//        List<AllWordDto> content = queryFactory
//                .select(ProductIdAndWordDto.create(word1.word, word1.definition))
//                .from(word1)
//                .leftJoin(wordProduct).on(wordProduct.word.eq(word1))
//                .leftJoin(wordProduct.product, QProduct.product)
//                .where(QProduct.product.eq(product))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        JPAQuery<Long> countQuery = queryFactory
//                .select(word1.count())
//                .from(word1)
//                .leftJoin(wordProduct).on(wordProduct.word.eq(word1))
//                .leftJoin(wordProduct.product, QProduct.product)
//                .where(QProduct.product.eq(product));
//
//        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
//
//
//    }






//    @Override
//    public Page<AllWordDto> searchAllWordPage(Product product, Pageable pageable) {
//        List<AllWordDto> content = queryFactory
//                .select(ProductIdAndWordDto.create(word1.word, word1.definition))
//                .from(word1)
//                .leftJoin(wordProduct).on(wordProduct.word.eq(word1))
//                .leftJoin(wordProduct.product, QProduct.product)
//                .where(QProduct.product.eq(product))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        JPAQuery<Long> countQuery = queryFactory
//                .select(word1.count())
//                .from(word1)
//                .leftJoin(wordProduct).on(wordProduct.word.eq(word1))
//                .leftJoin(wordProduct.product, QProduct.product)
//                .where(QProduct.product.eq(product));
//
//        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
//    }

    //-----


}