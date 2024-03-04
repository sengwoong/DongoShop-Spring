package com.gangE.DongoShop.repository;


import com.gangE.DongoShop.dto.QDto.ProductIdAndExamDto;
import com.gangE.DongoShop.dto.QDto.ProductIdAndWordDto;
import com.gangE.DongoShop.model.*;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.gangE.DongoShop.model.QExam.exam;
import static com.gangE.DongoShop.model.QExamProduct.examProduct;
import static com.gangE.DongoShop.model.QWordProduct.wordProduct;
import static org.springframework.util.StringUtils.isEmpty;


public class ExamRepositoryImpl implements ExamRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public ExamRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    //
    @Override
    public List<ProductIdAndExamDto> searchInAllExam(Product product) {
        List<ProductIdAndExamDto> exams = queryFactory
                .select(Projections.constructor(
                        ProductIdAndExamDto.class,
                        examProduct.product.id,
                        exam.id,
                        exam.title,
                        exam.content
                ))
                .from(exam)
                .innerJoin(exam.exam_product_id, examProduct)
                .where(examProduct.product.eq(product))
                .fetch();
        return exams;
    }

    @Override
    public ExamProduct searchInProductExam(Product product, long searchExam) {
        ExamProduct exams = queryFactory
                .select(examProduct)
                .from(examProduct)
                .innerJoin(examProduct.exam, exam)
                .where(
                        examProduct.product.eq(product),
                        examIdEq(searchExam)
                )
                .fetchFirst();

        return exams;
    }




    private BooleanExpression examIdEq(long searchExam) {
        return isEmpty(searchExam) ? null : exam.id.eq((int) searchExam);
    }



}