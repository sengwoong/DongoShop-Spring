package com.gangE.DongoShop.repository;


import com.gangE.DongoShop.dto.ProductType;
import com.gangE.DongoShop.model.Product;
import com.gangE.DongoShop.model.QProduct;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static com.gangE.DongoShop.model.QProduct.product;
import static org.springframework.util.StringUtils.isEmpty;


public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Autowired
    private EntityManager entityManager;
    @Override
    public Page<Product> searchInAllProducts(String type, String content, String downCountOrder, String currentOrder, int page, int size) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        OrderSpecifier<?>[] orderSpecifiers = createOrderSpecifiers(downCountOrder, currentOrder);

        QueryResults<Product> results = queryFactory
                .selectFrom(QProduct.product)
                .where(
                        productTitleContentSearch(content),
                        productTypeSearch(type)
                )
                .orderBy(orderSpecifiers)
                .offset(page * size)
                .limit(size)
                .fetchResults();

        List<Product> products = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(products, PageRequest.of(page, size), total);
    }



    private Predicate productTitleContentSearch(String content) {
        BooleanExpression titleExpression = isEmpty(content) ? null : product.title.contains(  content );
        BooleanExpression contentExpression = isEmpty(content) ? null : product.content.contains( content );

        if (titleExpression != null && contentExpression != null) {
            return contentExpression.or(titleExpression);
        } else if (titleExpression != null) {
            return titleExpression;
        } else if (contentExpression != null) {
            return contentExpression;
        } else {
            // contentExpression과 titleExpression 모두 null인 경우
            return null;
        }
    }
    private BooleanExpression productTypeSearch(String type) {
        BooleanExpression typeExpression = isEmpty(type) ? null : product.type.stringValue().contains( type );

        return typeExpression;
    }


    private OrderSpecifier[] createOrderSpecifiers(String downCountOrder, String currentOrder) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        if (downCountOrder != null) {
            switch (downCountOrder.toLowerCase()) {
                case "asc":
                    orderSpecifiers.add(new OrderSpecifier(Order.ASC,product.downloadCount));
                    break;
                case "desc":
                    orderSpecifiers.add(new OrderSpecifier(Order.DESC,product.downloadCount));
                    break;
            }
        }

        if (currentOrder != null) {
            switch (currentOrder.toLowerCase()) {
                case "old":
                    orderSpecifiers.add(new OrderSpecifier(Order.ASC,product.createdAt)); // 오래된 순 오름차순 정렬
                    break;
                case "recent":
                    orderSpecifiers.add(new OrderSpecifier(Order.DESC,product.createdAt)); // 최신순 내림차순 정렬
                    break;

            }
        }

        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }


}