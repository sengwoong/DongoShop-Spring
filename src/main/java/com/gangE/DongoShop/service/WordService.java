package com.gangE.DongoShop.service;




import com.gangE.DongoShop.dto.QDto.ProductIdAndWordDto;
import com.gangE.DongoShop.dto.WordDto;
import com.gangE.DongoShop.model.*;
import com.gangE.DongoShop.repository.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;



@Service
public class WordService   {

    private final WordRepository wordRepository;
    private final ProductRepository productRepository;
    private final WordProductRepository wordProductRepository;
    private final CustomerService customerService;
    @Autowired
    public WordService(WordRepository wordRepository, WordProductRepository wordProductRepository,
                       ProductRepository productRepository, CustomerRepository customerRepository ,  CustomerService customerService
                        ) {
        this.wordRepository = wordRepository;
        this.wordProductRepository = wordProductRepository;
        this.productRepository = productRepository;
        this.customerService = customerService;
    }

    @Autowired
    private JPAQueryFactory queryFactory;



    // 저장할때 프로덕트와 연결한 wordProduct를 넣어야한다

    @Transactional
    public Word saveWord(String wordText, String definition, Long productId) {

        if (definition == null || wordText == null || productId == null) {
            throw new IllegalArgumentException("단어, 정의 및 제품 ID를 모두 제공해야합니다.");
        }

        Word newWord = new Word();
        newWord.setWord(wordText);
        newWord.setDefinition(definition);
        wordRepository.save(newWord); // Save the new word

        // Retrieve the product
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("해당 ID에 해당하는 제품이 없습니다.");
        }

        Product product = optionalProduct.get();
        // 월드 프로덕트애 등록
        WordProduct wordProduct = new WordProduct();
        wordProduct.setProduct(product);
        wordProduct.setWord(newWord); // 새단어 하나만 등
        wordProductRepository.save(wordProduct); // 저장

        return newWord;
    }

    // todo  단어를 업데이트 하기위해 product 에있는 관리자 유저가 요청유저인지 확인한다음 업데이트
    @Transactional
    public WordProduct DelectMyWord(Long productId, Long wordId ) {
        // 전체 에서 해당 wordId 가 있는지 검색
        WordProduct MyWord= selectMyWordProduct(productId,wordId);
        // 해당 단어를삭제

        wordProductRepository.delete(MyWord);

        return MyWord;
    }


    @Transactional
    public Optional<Word> UpdateMyWord(Long productId, Long wordId ,WordDto word) {
        // 전체 에서 해당 wordId 가 있는지 검색
        Optional<Word> MyWord= selectWord(productId,wordId);
        // 해트단어를 업데이트

        MyWord.get().setWord(word.getWord());
        MyWord.get().setDefinition(word.getDefinition());

        // 엔티티가 자동업데이
        return MyWord;
    }




    @Transactional
    public List<ProductIdAndWordDto> selectAllWord(Long productId ) {

        if ( productId == null) {
            throw new IllegalArgumentException("해당 프로덕트의 모든단어를 검색할려면 프로덕트 아이디가 필요합니다.");
        }

        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("해당 ID에 해당하는 제품이 없습니다.");
        }

        Product product = optionalProduct.get();
        List<ProductIdAndWordDto> words =  wordRepository.searchInAllWord(product);

        return  words;
    }


    @Transactional
    public WordProduct selectMyWordProduct(Long productId, Long wordId ) {

        if ( productId == null || wordId==null) {
            throw new IllegalArgumentException("해당 프로덕트의 모든단어를 검색할려면 프로덕트 아이디와 단어아이디가 필요합니다.");
        }

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("해당 ID에 해당하는 제품이 없습니다.");
        }

        Product product = optionalProduct.get();
        if (product.getProductCustomer() !=  customerService.getCurrentCustomer()) {
            throw new IllegalArgumentException("나의 포스트가 아닙니다.");
        }

        WordProduct wordProducts =  wordRepository.searchInProductWord(product,wordId);

        return  wordProducts;
    }



    @Transactional
    public Optional<Word> selectWord(Long productId, Long wordId ) {

        if ( productId == null || wordId==null) {
            throw new IllegalArgumentException("해당 프로덕트의 모든단어를 검색할려면 프로덕트 아이디와 단어아이디가 필요합니다.");
        }

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("해당 ID에 해당하는 제품이 없습니다.");
        }

        Product product = optionalProduct.get();
        if (product.getProductCustomer() !=  customerService.getCurrentCustomer()) {
            throw new IllegalArgumentException("나의 포스트가 아닙니다.");
        }

        Optional<Word> word =  wordRepository.findById(wordId);

        if (word.isEmpty()) {
            throw new IllegalArgumentException("단어가 없습니다.");
        }

        return  word;
    }






}




