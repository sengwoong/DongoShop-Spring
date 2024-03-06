package com.gangE.DongoShop.service;


import com.gangE.DongoShop.dto.QDto.ProductIdAndExamDto;
import com.gangE.DongoShop.dto.ExamDto;
import com.gangE.DongoShop.model.Product;
import com.gangE.DongoShop.model.Exam;
import com.gangE.DongoShop.model.ExamProduct;
import com.gangE.DongoShop.repository.CustomerRepository;
import com.gangE.DongoShop.repository.ProductRepository;
import com.gangE.DongoShop.repository.ExamProductRepository;
import com.gangE.DongoShop.repository.ExamRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ExamService {

    private final ExamRepository ExamRepository;
    private final ProductRepository productRepository;
    private final ExamProductRepository ExamProductRepository;
    private final CustomerService customerService;

    @Autowired
    public ExamService(ExamRepository ExamRepository, ExamProductRepository ExamProductRepository, ProductRepository productRepository, CustomerRepository customerRepository,CustomerService customerService
                        ) {
        this.ExamRepository = ExamRepository;
        this.ExamProductRepository = ExamProductRepository;
        this.productRepository = productRepository;
        this.customerService = customerService;
    }

    @Autowired
    private JPAQueryFactory queryFactory;



    // 저장할때 프로덕트와 연결한 ExamProduct를 넣어야한다

    @Transactional
    public Exam saveExam(String ExamText, String definition, Long productId) {

        if (definition == null || ExamText == null || productId == null) {
            throw new IllegalArgumentException("단어, 정의 및 제품 ID를 모두 제공해야합니다.");
        }

        Exam newExam = new Exam();
        newExam.setTitle(ExamText);
        newExam.setContent(definition);
        ExamRepository.save(newExam); // Save the new Exam

        // Retrieve the product
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("해당 ID에 해당하는 제품이 없습니다.");
        }

        Product product = optionalProduct.get();
        // 월드 프로덕트애 등록
        ExamProduct ExamProduct = new ExamProduct();
        ExamProduct.setProduct(product);
        ExamProduct.setExam(newExam); // 새단어 하나만 등
        ExamProductRepository.save(ExamProduct); // 저장

        return newExam;
    }

    // todo  단어를 업데이트 하기위해 product 에있는 관리자 유저가 요청유저인지 확인한다음 업데이트
    @Transactional
    public ExamProduct DelectMyExam(Long productId, Long ExamId ) {
        // 전체 에서 해당 ExamId 가 있는지 검색
        ExamProduct MyExam= selectMyExamProduct(productId,ExamId);
        // 해당 단어를삭제

        ExamProductRepository.delete(MyExam);

        return MyExam;
    }


    @Transactional
    public Optional<Exam> UpdateMyExam(Long productId, Long ExamId ,ExamDto Exam) {
        // 전체 에서 해당 ExamId 가 있는지 검색
        Optional<Exam> MyExam= selectExam(productId,ExamId);
        // 해트단어를 업데이트

        MyExam.get().setTitle(Exam.getTitle());
        MyExam.get().setContent(Exam.getContent());

        // 엔티티가 자동업데이
        return MyExam;
    }




    @Transactional
    public List<ProductIdAndExamDto> selectAllExam(Long productId ) {

        if ( productId == null) {
            throw new IllegalArgumentException("해당 프로덕트의 모든단어를 검색할려면 프로덕트 아이디가 필요합니다.");
        }

        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("해당 ID에 해당하는 제품이 없습니다.");
        }

        Product product = optionalProduct.get();
        List<ProductIdAndExamDto> Exams =  ExamRepository.searchInAllExam(product);

        return  Exams;
    }


    @Transactional
    public ExamProduct selectMyExamProduct(Long productId, Long ExamId ) {

        if ( productId == null || ExamId==null) {
            throw new IllegalArgumentException("해당 프로덕트의 모든단어를 검색할려면 프로덕트 아이디와 단어아이디가 필요합니다.");
        }

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("해당 ID에 해당하는 제품이 없습니다.");
        }

        Product product = optionalProduct.get();
        if (product.getProductCustomer() != customerService.getCurrentCustomer()) {
            throw new IllegalArgumentException("나의 포스트가 아닙니다.");
        }

        ExamProduct ExamProducts =  ExamRepository.searchInProductExam(product,ExamId);

        return  ExamProducts;
    }



    @Transactional
    public Optional<Exam> selectExam(Long productId, Long ExamId ) {

        if ( productId == null || ExamId==null) {
            throw new IllegalArgumentException("해당 프로덕트의 모든단어를 검색할려면 프로덕트 아이디와 단어아이디가 필요합니다.");
        }

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("해당 ID에 해당하는 제품이 없습니다.");
        }

        Product product = optionalProduct.get();
        if (product.getProductCustomer() != customerService.getCurrentCustomer()) {

            throw new IllegalArgumentException("나의 포스트가 아닙니다.");
        }

        Optional<Exam> Exam =  ExamRepository.findById(ExamId);

        if (Exam.isEmpty()) {
            throw new IllegalArgumentException("단어가 없습니다.");
        }


        return  Exam;
    }







}




