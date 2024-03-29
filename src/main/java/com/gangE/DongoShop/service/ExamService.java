package com.gangE.DongoShop.service;


import com.gangE.DongoShop.dto.PrevIdCurrnetId;
import com.gangE.DongoShop.dto.QDto.ProductIdAndExamDto;
import com.gangE.DongoShop.dto.ExamDto;
import com.gangE.DongoShop.model.*;
import com.gangE.DongoShop.repository.CustomerRepository;
import com.gangE.DongoShop.repository.ProductRepository;
import com.gangE.DongoShop.repository.ExamProductRepository;
import com.gangE.DongoShop.repository.ExamRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityNotFoundException;
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
        if (product.getUser() != customerService.getCurrentCustomer()) {
            throw new IllegalArgumentException("나의 포스트가 아닙니다.");
        }

        ExamProduct ExamProducts =  ExamRepository.searchInProductExam(product,ExamId);

        return  ExamProducts;
    }


    private Product IsMyProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("해당 ID에 해당하는 제품이 없습니다.");
        }

        Customer customer =  customerService.getCurrentCustomer();
        if (customer == null) {
            throw new IllegalArgumentException("유저가없습니다.");
        }

        Product product = optionalProduct.get();
        if (!product.getUser().equals(customer)) {
            throw new IllegalArgumentException("나의 포스트가 아닙니다.");
        }
        return product;
    }




    @Transactional
    public Optional<Exam> IsMyExam(Long productId, Long ExamId ) {

        if ( productId == null || ExamId==null) {
            throw new IllegalArgumentException("해당 프로덕트의 모든단어를 검색할려면 프로덕트 아이디와 단어아이디가 필요합니다.");
        }

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("해당 ID에 해당하는 제품이 없습니다.");
        }

        Product product = optionalProduct.get();
        if (product.getUser() != customerService.getCurrentCustomer()) {

            throw new IllegalArgumentException("나의 포스트가 아닙니다.");
        }

        Optional<Exam> Exam =  ExamRepository.findById(ExamId);

        if (Exam.isEmpty()) {
            throw new IllegalArgumentException("단어가 없습니다.");
        }


        return  Exam;
    }



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

    @Transactional
    public void exchangeExamByLocalId(int productId, PrevIdCurrnetId wordLocal) {
        // 전체에서 해당 wordId가 있는지 검색
        ExamProduct prevWord = ExamProductRepository.findByProductIdAndExamLocal(productId, wordLocal.getPrevId());
        ExamProduct currentWord = ExamProductRepository.findByProductIdAndExamLocal(productId, wordLocal.getCurrentId());

        Product product =  IsMyProduct((long)productId);
        if(product == null){
            throw new IllegalArgumentException("해당 ID에 해당하는 제품이 없습니다.");
        }
        // 검색된 WordProduct가 null이 아닌 경우에만 로직을 진행
        if (prevWord != null && currentWord != null) {
            // prevWord와 currentWord를 서로 교환
            String temp = String.valueOf(prevWord.getExamLocal());
            prevWord.setExamLocal(currentWord.getExamLocal());
            currentWord.setExamLocal(Integer.parseInt(temp));
        } else {
            // 예외 처리 또는 로그 등의 추가 작업을 수행할 수 있음
            // 예를 들어, WordProduct가 찾아지지 않을 때 로그를 출력하거나 예외를 던질 수 있음
            throw new EntityNotFoundException("WordProduct not found for productId: " + productId + " and wordId: " + wordLocal);
        }
    }





    @Transactional
    public void DelectMyExam(Long productId, Long examId ) {
        // 전체 에서 해당 ExamId 가 있는지 검색
        Optional<Exam> MyExam= IsMyExam(productId,examId);
        // 해당 단어를삭제

        Optional<Exam> myExam = IsMyExam(productId, examId);

        if (!myExam.isPresent()) {
          return;
        }
        ExamRepository.delete(myExam.get());


    }









    @Transactional
    public Optional<Exam> UpdateMyExam(Long productId, Long ExamId ,ExamDto Exam) {
        // 전체 에서 해당 ExamId 가 있는지 검색
        Optional<Exam> MyExam= IsMyExam(productId,ExamId);
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










}




